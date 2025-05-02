package org.qudus.squad.logic.useCases.tasks

import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task

class CreateNewTaskUseCase(
    private val taskRepository: TaskRepository
) {
    fun createNewTask(task: Task){
        if (validateTaskValues(task)){
            taskRepository.createNewTask(task)
        }else
            throw EmptyValuesException(TASK_HAS_EMPTY_VALUES)
    }

    private fun validateTaskValues(task: Task): Boolean{
        return task.title.isNotBlank() && task.description.isNotBlank()
                && task.creatorUserID.isNotBlank() && task.projectId.isNotBlank()
                && task.taskState.name.isNotBlank()
    }

    companion object{
        const val TASK_HAS_EMPTY_VALUES = "Task has empty values"
    }
}