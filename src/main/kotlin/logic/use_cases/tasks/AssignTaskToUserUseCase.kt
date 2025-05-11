package logic.use_cases.tasks

import logic.use_cases.tasks.GetAllTasksByProjectIdUseCase.Companion.NO_TASK_FOUND
import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase

class AssignTaskToUserUseCase(
    private val taskRepository: TaskRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase,
    private val userDataValidationUseCase: UserDataValidationUseCase
) {
    suspend fun assignTaskToUser(userId : String, taskId : String): Boolean {
        if (taskDataValidationUseCase.validateTaskId(taskId) && userDataValidationUseCase.validateUserId(userId)){
            taskRepository.getTaskById(taskId) ?: throw TaskNotFoundException(NO_TASK_FOUND)
            return taskRepository.assignTaskToUser(taskId, userId)
        }
        return false
    }
}