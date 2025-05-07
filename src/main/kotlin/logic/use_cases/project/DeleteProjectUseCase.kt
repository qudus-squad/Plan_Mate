package logic.use_cases.project

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.ui.utils.GenerateUUID

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend fun deleteProjectUseCase(id:String = GenerateUUID().generate()): Boolean {
        return projectRepository.deleteProjectById(id)
    }
}