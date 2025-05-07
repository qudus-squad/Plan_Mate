package logic.use_cases.project

import logic.exceptions.AccessDeniedException
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class CreateNewProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val projectDataValidationUseCase: ProjectDataValidationUseCase
) {
    suspend fun createProject(
        user: User,
        title: String,
        description: String,
        tasks: List<Task> = emptyList()
    ): Project {
        if (user.role != UserRole.ADMIN) {
            throw AccessDeniedException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
        }
        val newProjectInfo = Project(
            id = GenerateUUID().generate(),
            title = title,
            creatorUserId = user.userId,
            description = description,
            tasks = tasks,
        )
        projectDataValidationUseCase.validateProjectData(newProjectInfo)
        val createdProject = projectRepository.createNewProject(newProjectInfo)
        return createdProject
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}