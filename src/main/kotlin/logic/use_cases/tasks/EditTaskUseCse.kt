package logic.use_cases.tasks

import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidator
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.Task

class EditTaskUseCse(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidator
) {
    fun editTask(userName: String, updatedTask: Task,action: String,oldValue: String, newValue: String){
        if (taskDataValidator.validateTaskValues(updatedTask)){
            taskRepository.editExistingTask(updatedTask)
            logRepository.addLog(
                LogEntry(
                    userName, updatedTask.id, TargetType.TASK, action, oldValue, newValue
                )
            )
        }else
            throw EmptyValuesException(TASK_HAS_EMPTY_VALUES)
    }

    companion object{
        const val TASK_HAS_EMPTY_VALUES = "Task has empty values"
    }
}