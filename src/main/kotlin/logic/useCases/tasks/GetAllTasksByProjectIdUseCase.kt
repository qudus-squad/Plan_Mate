package org.qudus.squad.logic.useCases.tasks

import org.qudus.squad.logic.exceptions.NoTasksFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task

class GetAllTasksByProjectIdUseCase(
    private val taskRepository: TaskRepository
) {

    fun getAllTasksByProjectId(id: String) : List<Task> {
        return taskRepository.getAllTasksByProjectId(id)
            .takeIf { it
                .isNotEmpty() } ?: throw NoTasksFoundException(NO_TASK_FOUND)
    }

    companion object {
        const val NO_TASK_FOUND = "No tasks found"
    }
}
