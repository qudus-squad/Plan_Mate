package logic.use_cases.project

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project

class GetAllProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    fun getAllProjectsUseCase(): List<Project> {
        return projectRepository.getAllProjects()
    }
}