package org.qudus.squad.ui.controlPanel

import logic.exceptions.TaskCreationException
import logic.exceptions.TaskNotFoundException
import logic.use_cases.tasks.*
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.tasks.UnAssignTaskUseCase
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.EditTaskInput
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.ui.tablesDisplay.TasksTableDisplay
import org.qudus.squad.ui.utils.DateTimeFormatter

class TaskManagement(
    private val tasksRepository: TaskRepository, private val user: User,
) {
    suspend fun createNewTask(id: String) {
        val repository: TaskRepository = getKoin().get()
        val validation: TaskDataValidationUseCase = getKoin().get()
        val logRepository: LogRepository = getKoin().get()
        val createNewTask = CreateNewTaskUseCase(
            repository,
            logRepository = logRepository,
            taskDataValidator = validation,
        )
        println("ENTER TASK NAME : ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER TASK DESCRIPTION : ")
        val descriptionSelected = readlnOrNull()?.trim() ?: ""


        println("ENTER TASK STATE NAME : ")
        val taskStateNameSelected = readlnOrNull()?.trim() ?: ""


        try {
            val addedTask = createNewTask.createNewTask(
                userName = user.username, task = Task(
                    title = titleSelected,
                    creatorUserID = user.userId,
                    description = descriptionSelected,
                    projectId = id,
                    taskState = TaskState(name = taskStateNameSelected),
                )
            )
            println("${addedTask.title} Task created Successfully")
        }catch (_: TaskCreationException){
            println("Sorry, some problem occur while creation task")
        }catch (_: TaskNotFoundException){
            println("Task created successfully but we can't show it here")
        }
    }

    suspend fun editTaskNameUsingId() {
        val repository: TaskRepository = getKoin().get()
        val validation: TaskDataValidationUseCase = getKoin().get()
        val logRepository: LogRepository = getKoin().get()
        val editTask = EditTaskUseCase(
            repository,
            logRepository = logRepository,
            taskDataValidator = validation,
        )
        println("ENTER TASK ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER NEW TITLE : ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
        val oldTask = tasksRepository.getTaskById(id = idSelected)
        if (oldTask != null) {

            editTask.editTask(
                EditTaskInput(
                    userName = user.username,
                    updatedTask = oldTask.copy(title = titleSelected),
                    action = "edit Task Name form ${oldTask.title} to $titleSelected",
                    oldValue = oldTask.title,
                    newValue = titleSelected,
                )
            )
        } else println("task updated successfully")
    }

    suspend fun editTaskDescriptionUsingId() {
        val repository: TaskRepository = getKoin().get()
        val validation: TaskDataValidationUseCase = getKoin().get()
        val logRepository: LogRepository = getKoin().get()
        val editTask = EditTaskUseCase(
            repository,
            logRepository = logRepository,
            taskDataValidator = validation,
        )
        println("ENTER TASK ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER NEW DESCRIPTION : ")
        val descriptionSelected = readlnOrNull()?.trim() ?: ""
        val oldTask = tasksRepository.getTaskById(id = idSelected)
        if (oldTask != null) {
            editTask.editTask(
                EditTaskInput(
                    userName = user.username,
                    updatedTask = oldTask.copy(description = descriptionSelected),
                    action = "edit Task description form ${oldTask.description} to $descriptionSelected",
                    oldValue = oldTask.description,
                    newValue = descriptionSelected,
                )
            )
            println("task updated successfully")
        } else println("No task found with ID : $idSelected")
    }

    suspend fun deleteTaskById(id: String) {
        val repository: TaskRepository = getKoin().get()
        val projectDataValidationUseCase: ProjectDataValidationUseCase = getKoin().get()
        val display = TasksTableDisplay(dateFormater = DateTimeFormatter)
        val ta = GetAllTasksByProjectIdUseCase(
            repository, projectDataValidationUseCase
        ).getAllTasksByProjectId(projectId = id)
        display.displayTasksTable(ta)

        val validation: TaskDataValidationUseCase = getKoin().get()
        val logRepository: LogRepository = getKoin().get()
        val deleteTask = DeleteTaskUseCase(
            repository,
            logRepository = logRepository,
            taskDataValidator = validation,
        )
        println("ENTER TASK ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        val task = tasksRepository.getTaskById(idSelected)
        if (task != null) {
            deleteTask.deleteTask(
                user.username, taskId = idSelected, taskTitle = task.title
            )
        } else println("")
        val oldTask = tasksRepository.getTaskById(id = idSelected)

        if (oldTask != null) {
            deleteTask.deleteTask(
                userName = user.username,
                taskId = idSelected,
                taskTitle = oldTask.title,
            )
            println("task updated successfully")
        } else println("No task found with ID : $idSelected")
    }

    suspend fun assignTask() {
        val repository: TaskRepository = getKoin().get()
        val taskDataValidationUseCase: TaskDataValidationUseCase = getKoin().get()
        val assignTaskToUser = AssignTaskToUserUseCase(
            taskRepository = repository,
            taskDataValidationUseCase = taskDataValidationUseCase,
        )

        println("ENTER USER ID : ")
        val userIdSelected = readlnOrNull()?.trim() ?: ""

        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""


        assignTaskToUser.assignTaskToUser(userId = userIdSelected, taskId = taskIdSelected)

    }

    suspend fun switchTaskState() {
        val repository: TaskRepository = getKoin().get()
        val unAssignTaskToUser = UnAssignTaskUseCase(taskRepository = repository)
        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""
        unAssignTaskToUser.unAssignTask(taskId = taskIdSelected)
    }
}