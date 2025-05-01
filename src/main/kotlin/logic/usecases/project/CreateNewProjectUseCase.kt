package org.qudus.squad.logic.usecases.project

import org.qudus.squad.logic.GenerateUUID
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.Project
import org.qudus.squad.model.State
import org.qudus.squad.model.User
import org.qudus.squad.model.UserRole
import org.qudus.squad.model.exceptions.AccessDeniedException

class CreateNewProjectUseCase(
    private val projectRepository: ProjectRepository,
) {
    fun createProject(
        user: User,
        title: String,
        description: String,
        state: List<State>
    ): Project {
        if (user.role != UserRole.ADMIN) {
            throw AccessDeniedException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
        }
        val newProjectInfo = Project(
            id = GenerateUUID().generate(),
            title = title,
            creatorUserId = user.userId,
            description = description,
            state = state,
        )
        val createdProject = projectRepository.createNewProject(newProjectInfo)
        return createdProject
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}