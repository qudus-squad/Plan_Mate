package logic.use_cases.tasks

import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.Task

class AssignTaskToUserUseCase(
    private val taskRepository: TaskRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase,
    private val logRepository: LogRepository
) {
    suspend fun assignTaskToUser(userId: String, taskId: String): Boolean {
        if (taskDataValidationUseCase.validateAssignTaskValues(userId, taskId)) logRepository.addNewLog(
            LogEntry(
                userName = userId,
                targetId = taskId,
                targetType = TargetType.USER,
                action = "assign task from user $userId",
            )
        )

        return taskRepository.assignTaskToUser(taskId, userId)
    }
}