package org.qudus.squad.logic.useCases.tasks

import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidator
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidator
) {
    fun deleteTask(userName: String, taskId: String, taskName: String){
        if (taskDataValidator.validateDeleteTaskValues(userName,taskId,taskName)){
            taskRepository.deleteTaskById(taskId)
            logRepository.addLog(LogEntry(userName,taskId, TargetType.TASK,"$taskName Task Deleted",null,null))
        }
        else
            throw EmptyValuesException(TASK_ID_IS_EMPTY)
    }

    companion object{
        const val TASK_ID_IS_EMPTY = "Task Id is Empty"
    }
}