package logic.use_cases.project

import logic.exceptions.AccessDeniedException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.model.entity.*

class CreateNewProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val projectDataValidationUseCase: ProjectDataValidationUseCase,
    private val logRepository: LogRepository,
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
        logRepository.addNewLog(LogEntry(
            userName = user.username,
            targetId = createdProject.id,
            targetType = TargetType.PROJECT,
            action = "${createdProject.id} Created Project",
            oldValue = "title $title | description $description",
            newValue = "title ${createdProject.title} | description ${createdProject.description}",
        ))
        return createdProject
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}