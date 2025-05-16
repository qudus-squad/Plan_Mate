package logic.use_cases.tasks

import logic.exceptions.InvalidProjectIdException
import logic.exceptions.InvalidProjectTitleException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase.Companion.INVALID_PROJECT_ID
import org.qudus.squad.model.entity.Task

class GetAllTasksByProjectIdUseCase(
    private val taskRepository: TaskRepository, private val projectDataValidationUseCase: ProjectDataValidationUseCase
) {
 suspend fun getAllTasksByProjectId(projectId: String): List<Task> {
        if (!projectDataValidationUseCase.validateProjectId(projectId)) throw InvalidProjectIdException(
            INVALID_PROJECT_ID
        )
        return taskRepository.getAllTasksByProjectId(projectId)
    }

    companion object {
        const val NO_TASK_FOUND = "No tasks found"
    }
}
