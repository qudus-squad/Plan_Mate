package org.qudus.squad.logic.use_cases.project

import logic.exceptions.InvalidProjectIDException
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project

class GetProjectByIdUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend fun getProjectById(projectId: String): Project {
        return if (projectId.isNotEmpty()) {
            projectRepository.getProjectById(projectId)
        } else {
            throw InvalidProjectIDException("ID should not be empty!")
        }
    }
}