package logic.use_cases.tasks

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidationUseCase
) {
    suspend fun deleteTask(userName: String, taskId: String, taskTitle: String): Boolean {
        if (!taskDataValidator.validateDeleteTaskValues(userName, taskId, taskTitle)) {
            return false
        }
        val result = taskRepository.deleteTaskById(taskId)
        if (result) {
            logRepository.addNewLog(
                LogEntry(
                    userName = userName,
                    targetId = taskId,
                    targetType = TargetType.TASK,
                    action = "$taskTitle Task Deleted"
                )
            )
        }
        return result
    }
}