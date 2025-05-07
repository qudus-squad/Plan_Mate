package logic.use_cases.tasks

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.Task

class EditTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidationUseCase
) {
    suspend fun editTask(userName: String, updatedTask: Task,action: String,oldValue: String, newValue: String){
        if (taskDataValidator.validateTaskValues(updatedTask)){
            taskRepository.editExistingTask(updatedTask)
            logRepository.addNewLog(LogEntry(userName, updatedTask.id, TargetType.TASK, action, oldValue, newValue))
        }
    }
}