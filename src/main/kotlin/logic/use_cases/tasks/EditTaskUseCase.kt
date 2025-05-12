package logic.use_cases.tasks

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.TargetType
import org.qudus.squad.logic.model.Task

class EditTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidationUseCase
) {
    suspend fun editTask(editTaskInput: EditTaskInput) {
        if (taskDataValidator.validateTaskValues(editTaskInput.updatedTask)) {
            taskRepository.editExistingTask(editTaskInput.updatedTask)
            logRepository.addNewLog(
                LogEntry(
                    editTaskInput.userName,
                    editTaskInput.updatedTask.id,
                    TargetType.TASK,
                    editTaskInput.action,
                    editTaskInput.oldValue,
                    editTaskInput.newValue
                )
            )
        }
    }
}

data class EditTaskInput(
    val userName: String, val updatedTask: Task, val action: String, val oldValue: String, val newValue: String
)

