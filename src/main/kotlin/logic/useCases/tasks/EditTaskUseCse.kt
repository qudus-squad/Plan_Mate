package org.qudus.squad.logic.useCases.tasks

import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidator
import org.qudus.squad.model.entity.Task

class EditTaskUseCse(
    private val taskRepository: TaskRepository
) {
    fun editTask(updatedTask: Task){
        if (TaskDataValidator.validateTaskValues(updatedTask)){
            taskRepository.editExistingTask(updatedTask)
        }else
            throw EmptyValuesException(TASK_HAS_EMPTY_VALUES)
    }

    companion object{
        const val TASK_HAS_EMPTY_VALUES = "Task has empty values"
    }
}