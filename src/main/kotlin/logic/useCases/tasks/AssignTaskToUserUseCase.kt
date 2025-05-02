package org.qudus.squad.logic.useCases.tasks

import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.exceptions.NoTasksFoundException

class AssignTaskToUserUseCase(
    private val taskRepository: TaskRepository,
) {
    fun assignTaskToUser(userId: String, taskId: String): Boolean {
        return if (taskRepository.getTaskById(taskId) == null) {
            throw NoTasksFoundException(NO_TASK_FOUND)
        } else {
            try {
                taskRepository.assignTaskToUser(taskId, userId)
            } catch (e: Exception) {
                throw e
            }
        }
    }


    companion object {
        const val NO_TASK_FOUND = "No tasks found"
    }
}