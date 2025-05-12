package logic.use_cases.tasks

import logic.exceptions.InvalidProjectTitleException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase.Companion.INVALID_PROJECT_TITLE
import org.qudus.squad.logic.model.Task

class GetAllTasksByProjectIdUseCase(
    private val taskRepository: TaskRepository, private val projectDataValidationUseCase: ProjectDataValidationUseCase
) {
    suspend fun getAllTasksByProjectId(projectId: String): List<Task> {
        if (!projectDataValidationUseCase.isValidProjectTitle(projectId)) throw InvalidProjectTitleException(
            INVALID_PROJECT_TITLE
        )
        return taskRepository.getAllTasksByProjectId(projectId)
    }

    companion object {
        const val NO_TASK_FOUND = "No tasks found"
    }
}
