package org.qudus.squad.logic.useCases.tasks

import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidator
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.Task

class CreateNewTaskUseCase(
    private val taskRepository: TaskRepository,
    private val logRepository: LogRepository,
    private val taskDataValidator: TaskDataValidator
) {
    fun createNewTask(userName: String, task: Task){
        if (taskDataValidator.validateTaskValues(task)){
            taskRepository.createNewTask(task)
            logRepository.addLog(
                LogEntry(
                    userName, task.id, TargetType.TASK, "add new task", null, null
                )
            )
        }else
            throw EmptyValuesException(TASK_HAS_EMPTY_VALUES)
    }

    companion object{
        const val TASK_HAS_EMPTY_VALUES = "Task has empty values"
    }
}