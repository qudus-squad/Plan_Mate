package logic.use_cases.project

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.User
import org.qudus.squad.ui.utils.GenerateUUID

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository,
) {
    suspend fun deleteProject(user: User, id: String = GenerateUUID().generate()): Boolean {
        logRepository.addNewLog(
            LogEntry(
                userName = user.username,
                targetId = id,
                targetType = TargetType.PROJECT,
                action = "$id Deleted Project",
            )
        )
        return projectRepository.deleteProjectById(id)
    }
}