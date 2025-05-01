package org.qudus.squad.logic.usecases

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.Project

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    fun deleteProjectUseCase(id: String) {
        return projectRepository.deleteProjectById(id)
    }
}