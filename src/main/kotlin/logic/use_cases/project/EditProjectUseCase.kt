package logic.use_cases.project


import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project


class EditProjectUseCase(
    private val projectRepository: ProjectRepository,
) {
    suspend fun editProject(project: Project): Boolean {
        return projectRepository.editProject(project)
    }
}


