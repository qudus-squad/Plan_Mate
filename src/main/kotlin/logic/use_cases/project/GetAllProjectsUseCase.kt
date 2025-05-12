package logic.use_cases.project

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.model.Project

class GetAllProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend fun getAllProjects(): List<Project> {
        return projectRepository.getAllProjects()
    }
}