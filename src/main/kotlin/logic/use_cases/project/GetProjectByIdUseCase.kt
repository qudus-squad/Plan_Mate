package org.qudus.squad.logic.use_cases.project

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.model.entity.Project

class GetProjectByIdUseCase(
    private val projectRepository: ProjectRepository,
    private val projectDataValidationUseCase: ProjectDataValidationUseCase
) {
    suspend fun getProjectById(projectId: String): Project {
        projectDataValidationUseCase.validateProjectId(projectId)
        return projectRepository.getProjectById(projectId)
    }
}