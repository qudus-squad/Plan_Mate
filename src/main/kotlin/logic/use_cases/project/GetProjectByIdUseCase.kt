package org.qudus.squad.logic.use_cases.project

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project

class GetProjectByIdUseCase(
    private val projectRepository: ProjectRepository
) {
    fun getProjectById(projectId: String): Project{
        return projectRepository.getProjectById(projectId)
    }
}