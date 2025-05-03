package logic.use_cases.tasks

import org.qudus.squad.logic.exceptions.NoTasksFoundException
import org.qudus.squad.logic.repositories.TaskRepository

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