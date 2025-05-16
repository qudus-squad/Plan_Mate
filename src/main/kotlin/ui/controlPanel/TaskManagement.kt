package org.qudus.squad.ui.controlPanel

import logic.exceptions.InvalidTaskIdException
import logic.use_cases.tasks.*
import logic.use_cases.user.GetAllUsersUseCase
import org.qudus.squad.data.data_source.task_data_source.*
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.tasks.GetAllTasksUseCase
import org.qudus.squad.logic.use_cases.tasks.GetTaskByIdUseCase
import org.qudus.squad.logic.use_cases.tasks.UnAssignTaskUseCase
import org.qudus.squad.model.entity.EditTaskInput
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.tablesDisplay.TasksTableDisplay
import org.qudus.squad.ui.utils.DateTimeFormatter

class TaskManagement(
    private val loginSession: LoginSession,
    private val createNewTasUseCase: CreateNewTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val assignTaskToUserUseCase: AssignTaskToUserUseCase,
    private val unAssignTaskToUserUseCase: UnAssignTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val taskRepository: TaskRepository
) {
    suspend fun createNewTask(id: String) {

        println("ENTER TASK NAME : ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER TASK DESCRIPTION : ")
        val descriptionSelected = readlnOrNull()?.trim() ?: ""


        println("ENTER TASK STATE NAME : ")
        val taskStateNameSelected = readlnOrNull()?.trim() ?: ""

        createNewTasUseCase.createNewTask(
            userName = loginSession.currentUser.username, task = Task(
                title = titleSelected,
                creatorUserID = loginSession.currentUser.userId,
                description = descriptionSelected,
                projectId = id,
                taskState = TaskState(name = taskStateNameSelected),
            )
        )
    }

    suspend fun editTaskNameUsingId() {

        val display = TasksTableDisplay(dateFormater = DateTimeFormatter)
        try {
            val tasks = getAllTasksUseCase.getAllTasks()
            if (tasks.isEmpty()){
                println("There are no tasks in your")
            }
            display.displayTasksTable(tasks)
            println("ENTER TASK ID : ")
            val idSelected = readlnOrNull()?.trim() ?: ""
            println("ENTER NEW TITLE : ")
            val titleSelected = readlnOrNull()?.trim() ?: ""
            val oldTask = getTaskByIdUseCase.getTaskById(taskId = idSelected)

            editTaskUseCase.editTask(
                EditTaskInput(
                    userName = loginSession.currentUser.username,
                    updatedTask = oldTask.copy(title = titleSelected),
                    action = "edit Task Name form ${oldTask.title} to $titleSelected",
                    oldValue = oldTask.title,
                    newValue = titleSelected,
                )
            )
            println("Task edited task name Successfully")
        } catch (e: InvalidToEditTaskException) {
            println(e.message)
        } catch (e: InvalidToGetTaskByIdTaskException) {
            println(e.message)
        } catch (e: NoFoundTaskException) {
            println(e.message)
        } catch (e: InvalidTaskIdException) {
            println(e.message)
        }
    }

    suspend fun editTaskDescriptionUsingId() {
        val display = TasksTableDisplay(dateFormater = DateTimeFormatter)
        try {
            val tasks = getAllTasksUseCase.getAllTasks()
            display.displayTasksTable(tasks)
            println("ENTER TASK ID : ")
            val idSelected = readlnOrNull()?.trim() ?: ""
            println("ENTER NEW DESCRIPTION : ")
            val descriptionSelected = readlnOrNull()?.trim() ?: ""

            val oldTask = getTaskByIdUseCase.getTaskById(taskId = idSelected)
            editTaskUseCase.editTask(
                EditTaskInput(
                    userName = loginSession.currentUser.username,
                    updatedTask = oldTask.copy(description = descriptionSelected),
                    action = "edit Task description form ${oldTask.description} to $descriptionSelected",
                    oldValue = oldTask.description,
                    newValue = descriptionSelected,
                )
            )
            println("Task edited task description Successfully")
        } catch (e: InvalidToGetTaskByIdTaskException) {
            println(e.message)
        } catch (e: InvalidToEditTaskException) {
            println(e.message)
        } catch (e: NoFoundTaskException) {
            println(e.message)
        } catch (e: InvalidTaskIdException) {
            println(e.message)
        }
    }

    suspend fun deleteTaskById(id: String) {
        try {
            val tasks = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projectId = id)
            val display = TasksTableDisplay(dateFormater = DateTimeFormatter)
            display.displayTasksTable(tasks)
            println("ENTER TASK ID : ")
            val idSelected = readlnOrNull()?.trim() ?: ""

            val task = getTaskByIdUseCase.getTaskById(idSelected)
            val result = deleteTaskUseCase.deleteTask(
                userName = loginSession.currentUser.username,
                taskId = idSelected,
                taskTitle = task.title,
            )
            if (result) {
                println("Task deleted successfully")
            }
        } catch (e: InvalidToDeleteTaskException) {
            println(e.message)
        } catch (e: InvalidToGetAllTasksException) {
            println(e.message)
        } catch (e: NoFoundTaskException) {
            println(e.message)
        }
    }

    suspend fun assignTask() {
        val users = getAllUsersUseCase.getAllUsers()
        if (users.isEmpty()) {
            println("no users found. Looks like the apocalypse started with our squad")
            return
        }
        println("AVAILABLE USERS:")
        users.forEachIndexed { index, user ->
            println("${index + 1}- ${user.username} [${user.role}]")
        }

        println("SELECT USER NUMBER TO ASSIGN TASK:")
        val selectedUserIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)

        if (selectedUserIndex == null || selectedUserIndex !in users.indices) {
            println("user not found. self-destruct sequence initiated...just kidding.")
            return
        }

        val selectedUser = users[selectedUserIndex]
        val allTasks = taskRepository.getAllTasks()
        val unassignedTasks = allTasks.filter { it.assignedUserId == null }

        if (unassignedTasks.isEmpty()) {
            println("all tasks are assigned. time to make your great escape (;")
            return
        }
        println("AVAILABLE TASKS:")
        unassignedTasks.forEachIndexed { index, task ->
            println("${index + 1}- ${task.title} (ID: ${task.id})")
        }

        println("SELECT TASK NUMBER TO ASSIGN TO USER '${selectedUser.username}':")
        val selectedTaskIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)

        if (selectedTaskIndex == null || selectedTaskIndex !in unassignedTasks.indices) {
            println("nice try. but that task doesn’t exist.")
            return
        }

        val selectedTask = unassignedTasks[selectedTaskIndex]

        try {
            assignTaskToUserUseCase.assignTaskToUser(
                userId = selectedUser.userId,
                taskId = selectedTask.id
            )
            println("${selectedUser.username} adopted '${selectedTask.title}'. hope they don’t return it!")
        } catch (e: InvalidToEditTaskException) {
            println(e.message)
        }
    }

    suspend fun switchTaskState() {
        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""

        try {
            unAssignTaskToUserUseCase.unAssignTask(taskId = taskIdSelected)
            println("Task state switched (unassigned) successfully.")
        } catch (e: InvalidToEditTaskException) {
            println(e.message)
        }
    }
}