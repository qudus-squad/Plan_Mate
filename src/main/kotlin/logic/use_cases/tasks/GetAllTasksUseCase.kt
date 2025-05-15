package org.qudus.squad.logic.use_cases.tasks

import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task

class GetAllTasksUseCase(
    private val taskRepository: TaskRepository
) {
    suspend fun getAllTasks(): List<Task> {
        return taskRepository.getAllTasks()
    }
}