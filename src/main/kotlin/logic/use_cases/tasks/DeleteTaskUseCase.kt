package logic.use_cases.tasks

import org.qudus.squad.logic.exceptions.EmptyValuesException
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
    fun deleteTask(userName: String, taskId: String, taskName: String){
        if (taskDataValidator.validateDeleteTaskValues(userName,taskId,taskName)){
            taskRepository.deleteTaskById(taskId)
            logRepository.addNewLog(LogEntry(userName,taskId, TargetType.TASK,"$taskName Task Deleted",null,null))
        }
        else
            throw EmptyValuesException(TASK_ID_IS_EMPTY)
    }

    companion object{
        const val TASK_ID_IS_EMPTY = "Task Id is Empty"
    }
}