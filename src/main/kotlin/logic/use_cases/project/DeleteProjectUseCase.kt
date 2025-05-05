package logic.use_cases.project

import org.qudus.squad.logic.repositories.ProjectRepository

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    fun deleteProjectUseCase(id: String): Boolean {
        return projectRepository.deleteProjectById(id)
    }
}