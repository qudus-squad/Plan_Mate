package org.qudus.squad.logic.usecases.project

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.Project

class GetAllProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    fun getAllProjectsUseCase(): List<Project> {
        return projectRepository.getAllProjects()
    }
}