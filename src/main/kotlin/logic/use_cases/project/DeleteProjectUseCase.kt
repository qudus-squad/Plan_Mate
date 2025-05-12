package logic.use_cases.project

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.TargetType
import org.qudus.squad.logic.model.User

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository,
    private val userValidationUseCase: UserDataValidationUseCase
) {
    suspend fun deleteProject(user: User, projectId: String): Boolean {
        userValidationUseCase.validateUserData(user.username,user.passwordHash)
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