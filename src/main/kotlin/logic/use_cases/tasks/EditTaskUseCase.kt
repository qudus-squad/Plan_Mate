package logic.use_cases.tasks

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.EditTaskInput
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType

class EditTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidationUseCase
) {
    suspend fun editTask(editTaskInput: EditTaskInput): Boolean {
        if (!taskDataValidator.validateTaskValues(editTaskInput.updatedTask)) {
            return false
        }

        val result = taskRepository.editExistingTask(editTaskInput.updatedTask)
        if (result) {
            logRepository.addNewLog(
                LogEntry(
                    userName = editTaskInput.userName,
                    targetId = editTaskInput.updatedTask.id,
                    targetType = TargetType.TASK,
                    action = editTaskInput.action,
                    oldValue = editTaskInput.oldValue,
                    newValue = editTaskInput.newValue
                )
            )
        }
        return result
    }
}