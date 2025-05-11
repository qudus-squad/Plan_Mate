package logic.use_cases.project

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.User

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository,
) {
    suspend fun deleteProject(user: User, projectId: String): Boolean {
        logRepository.addNewLog(
            LogEntry(
                userName = user.username,
                targetId = projectId,
                targetType = TargetType.PROJECT,
                action = "$projectId Deleted Project",
            )
        )
        return projectRepository.deleteProjectById(projectId)
    }
}