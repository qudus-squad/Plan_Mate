package logic.use_cases.project

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.utils.GenerateUUID

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    fun deleteProjectUseCase(id:String = GenerateUUID().generate()): Boolean {
        return projectRepository.deleteProjectById(id)
    }
}