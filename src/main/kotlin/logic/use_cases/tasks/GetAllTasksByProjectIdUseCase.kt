package logic.use_cases.tasks

import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task

class GetAllTasksByProjectIdUseCase(
    private val taskRepository: TaskRepository
) {

    fun getAllTasksByProjectId(id: String) : List<Task> {
        return taskRepository.getAllTasksByProjectId(id)
            .takeIf { it
                .isNotEmpty() } ?: throw TaskNotFoundException(NO_TASK_FOUND)
    }

    companion object {
        const val NO_TASK_FOUND = "No tasks found"
    }
}
