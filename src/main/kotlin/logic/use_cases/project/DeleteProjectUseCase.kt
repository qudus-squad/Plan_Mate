package logic.use_cases.project

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.User

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository,
    private val userValidationUseCase: UserDataValidationUseCase
) {
    suspend fun deleteProject(user: User, projectId: String): Boolean {
     if(!userValidationUseCase.validateUserData(user.username,user.passwordHash))
         return false
        val result = projectRepository.deleteProjectById(projectId)
        if (result) {
            logRepository.addNewLog(
                LogEntry(
                    userName = user.username,
                    targetId = projectId,
                    targetType = TargetType.PROJECT,
                    action = "$projectId Deleted Project",
                )
            )

        }
        return result
    }
}