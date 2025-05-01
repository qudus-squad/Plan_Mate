package org.qudus.squad.logic.useCases.project

import org.qudus.squad.logic.repositories.ProjectRepository

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    fun deleteProjectUseCase(id: String) {
        return projectRepository.deleteProjectById(id)
    }
}