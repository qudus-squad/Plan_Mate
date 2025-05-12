package logic.use_cases.tasks

import logic.use_cases.tasks.GetAllTasksByProjectIdUseCase.Companion.NO_TASK_FOUND
import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase

class AssignTaskToUserUseCase(
    private val taskRepository: TaskRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase
) {
    suspend fun assignTaskToUser(userId: String, taskId: String): Boolean {
        if (!taskDataValidationUseCase.validateAssignTaskValues(userId, taskId)) taskRepository.getTaskById(taskId)
            ?: throw TaskNotFoundException(NO_TASK_FOUND)
        return taskRepository.assignTaskToUser(taskId, userId)
    }
}