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
            throw EmptyValuesException("Task has empty values")
    }

    private fun validateTaskValues(task: Task): Boolean{
        return task.title.isNotBlank() && task.description.isNotBlank()
                && task.creatorUserID.isNotBlank() && task.projectId.isNotBlank()
                && task.taskState.name.isNotBlank()
    }
}