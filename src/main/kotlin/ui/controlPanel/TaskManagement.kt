package org.qudus.squad.ui.controlPanel

import logic.use_cases.tasks.*
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.data.data_source.task_data_source.*
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.tasks.UnAssignTaskUseCase
import org.qudus.squad.model.entity.EditTaskInput
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.tablesDisplay.TasksTableDisplay
import org.qudus.squad.ui.utils.DateTimeFormatter

class TaskManagement(
    private val tasksRepository: TaskRepository,
    private val loginSession: LoginSession,
    private val createNewTasUseCase: CreateNewTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase,
    private val assignTaskToUserUseCase: AssignTaskToUserUseCase,
    private val unAssignTaskToUserUseCase: UnAssignTaskUseCase
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
            val tasks = tasksRepository.getAllTasks()
            display.displayTasksTable(tasks)
            println("ENTER TASK ID : ")
            val idSelected = readlnOrNull()?.trim() ?: ""
            println("ENTER NEW TITLE : ")
            val titleSelected = readlnOrNull()?.trim() ?: ""
            val oldTask = tasksRepository.getTaskById(id = idSelected)

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
        } catch (_: InvalidToEditTaskException) {
            println(FAILED_EDIT_TASK)
        } catch (_: InvalidToGetTaskByIdTaskException) {
            println(FAILED_GET_TASK_BY_ID)
        } catch (_: NoFoundTaskException) {
            println(FAILED_To_Find_TASK)
        }
    }

    suspend fun editTaskDescriptionUsingId() {
        val display = TasksTableDisplay(dateFormater = DateTimeFormatter)
        try {
            val tasks = tasksRepository.getAllTasks()
            display.displayTasksTable(tasks)
            println("ENTER TASK ID : ")
            val idSelected = readlnOrNull()?.trim() ?: ""
            println("ENTER NEW DESCRIPTION : ")
            val descriptionSelected = readlnOrNull()?.trim() ?: ""

            val oldTask = tasksRepository.getTaskById(id = idSelected)
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
        } catch (_: InvalidToGetTaskByIdTaskException) {
            println(FAILED_GET_TASK_BY_ID)
        } catch (_: InvalidToEditTaskException) {
            println(FAILED_EDIT_TASK)
        } catch (_: NoFoundTaskException) {
            println(FAILED_To_Find_TASK)
        }
    }

    suspend fun deleteTaskById(id: String) {

        val repository: TaskRepository = getKoin().get()

        try {

            val tasks = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projectId = id)
            val display = TasksTableDisplay(dateFormater = DateTimeFormatter)
            display.displayTasksTable(tasks)
            println("ENTER TASK ID : ")
            val idSelected = readlnOrNull()?.trim() ?: ""

            val task = repository.getTaskById(idSelected)
            val result = deleteTaskUseCase.deleteTask(
                userName = loginSession.currentUser.username,
                taskId = idSelected,
                taskTitle = task.title,
            )
            if (result) {
                println("Task deleted successfully")
            }
        } catch (_: InvalidToDeleteTaskException) {
            println(FAILED_DELETE_TASK)
        } catch (_: InvalidToGetAllTasksException) {
            println(FAILED_GET_TASK_BY_ID)
        } catch (_: NoFoundTaskException) {
            println(FAILED_To_Find_TASK)
        }
    }


    suspend fun assignTask() {
        println("ENTER USER ID : ")
        val userIdSelected = readlnOrNull()?.trim() ?: ""

        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""

        try {
            assignTaskToUserUseCase.assignTaskToUser(userId = userIdSelected, taskId = taskIdSelected)
            println("Task successfully assigned to user.")
        } catch (_: InvalidToEditTaskException) {
            println(FAILED_ASSIGN_TASK)
        } catch (_: InvalidToEditTaskException) {
            println(FAILED_ASSIGN_TASK)
        }
    }

    suspend fun switchTaskState() {
        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""

        try {
            unAssignTaskToUserUseCase.unAssignTask(taskId = taskIdSelected)
            println("Task state switched (unassigned) successfully.")
        } catch (_: InvalidToEditTaskException) {
            println(FAILED_UNASSIGN_TASK)
        }
    }
}