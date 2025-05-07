package logic.use_cases.tasks

import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task

class GetAllTasksByProjectIdUseCase(
    private val taskRepository: TaskRepository
) {
    suspend fun getAllTasksByProjectId(projectId: String) : List<Task> {
        return taskRepository.getAllTasksByProjectId(projectId)
    }

    companion object {
        const val NO_TASK_FOUND = "No tasks found"
    }
}
