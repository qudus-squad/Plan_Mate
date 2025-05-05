package logic.use_cases.project

import org.qudus.squad.logic.exceptions.AccessDeniedException
import org.qudus.squad.logic.exceptions.InvalidProjectInfo
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class CreateNewProjectUseCase(
    private val projectRepository: ProjectRepository,
) {
    fun createProject(
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
        if (title.isEmpty()) throw InvalidProjectInfo(INVALID_PROJECT_INFO)
        val createdProject = projectRepository.createNewProject(newProjectInfo)
        return createdProject
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
        const val INVALID_PROJECT_INFO = "Project Name Is Empty "
    }
}