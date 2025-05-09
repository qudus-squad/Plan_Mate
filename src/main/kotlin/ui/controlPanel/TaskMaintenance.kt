package org.qudus.squad.ui.controlPanel

import logic.use_cases.tasks.AssignTaskToUserUseCase
import logic.use_cases.tasks.CreateNewTaskUseCase
import logic.use_cases.tasks.DeleteTaskUseCase
import logic.use_cases.tasks.EditTaskUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.tasks.UnAssignTaskUseCase
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User

class TaskMaintenance(
    private val tasksRepository: TaskRepository, private val user: User,
) {
    suspend fun createNewTask() {
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
        println("ENTER TASK PROJECT_ID : ")
        val projectIdSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER TASK STATE NAME : ")
        val taskStateNameSelected = readlnOrNull()?.trim() ?: ""

        createNewTask.createNewTask(
            userName = "", task = Task(
                title = titleSelected,
                description = descriptionSelected,
                projectId = projectIdSelected,
                taskState = TaskState(name = taskStateNameSelected),
            )
        )
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
                userName = user.username,
                updatedTask = oldTask.copy(title = titleSelected),
                action = "edit Task Name form ${oldTask.title} to $titleSelected",
                oldValue = oldTask.title,
                newValue = titleSelected,
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
                userName = user.username,
                updatedTask = oldTask.copy(description = descriptionSelected),
                action = "edit Task description form ${oldTask.description} to $descriptionSelected",
                oldValue = oldTask.description,
                newValue = descriptionSelected,
            )
            println("task updated successfully")
        } else println("No task found with ID : $idSelected")
    }

    suspend fun deleteTaskById() {
        val repository: TaskRepository = getKoin().get()
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
                user.username,
                taskId = idSelected,
                taskTitle =task.title
            )
        }else println("")
        val oldTask = tasksRepository.getTaskById(id = idSelected)

        if (oldTask != null) {
            deleteTask.deleteTask(
                userName = user.username,
                taskId = idSelected,
                taskTitle = oldTask.title,
            )
            println("task updated successfully")
        }
        else println("No task found with ID : $idSelected")
    }
    suspend fun assignTask() {
        val repository: TaskRepository = getKoin().get()
        val assignTaskToUser = AssignTaskToUserUseCase(taskRepository =repository)

        println("ENTER USER ID : ")
        val userIdSelected = readlnOrNull()?.trim() ?: ""

        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""


        assignTaskToUser.assignTaskToUser(userId = userIdSelected, taskId = taskIdSelected)

    }
    suspend fun switchTaskState() {
        val repository: TaskRepository = getKoin().get()
        val unAssignTaskToUser = UnAssignTaskUseCase(taskRepository =repository)
        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""
        unAssignTaskToUser.unAssignTask(taskId = taskIdSelected)
    }
}