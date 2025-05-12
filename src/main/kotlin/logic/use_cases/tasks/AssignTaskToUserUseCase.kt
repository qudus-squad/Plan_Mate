package logic.use_cases.tasks

import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.Task

class AssignTaskToUserUseCase(
    private val taskRepository: TaskRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase
) {
    suspend fun assignTaskToUser(userId: String, taskId: String): Boolean {
        var task : Task? = null
        if (taskDataValidationUseCase.validateAssignTaskValues(userId, taskId)) task = taskRepository.getTaskById(taskId)
        if(task == null) throw TaskNotFoundException(GetAllTasksByProjectIdUseCase.NO_TASK_FOUND)
        return taskRepository.assignTaskToUser(taskId, userId)
    }
}