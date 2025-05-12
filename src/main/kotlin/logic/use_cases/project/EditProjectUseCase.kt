package logic.use_cases.project


import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.Project
import org.qudus.squad.logic.model.TargetType
import org.qudus.squad.logic.model.User


class EditProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository,
    private val userDataValidationUseCase: UserDataValidationUseCase,
    private val projectValidationUseCase: ProjectDataValidationUseCase
) {
    suspend fun editProject(user: User, project: Project): Project {
        userDataValidationUseCase.validateUserData(user.username, user.passwordHash)
        projectValidationUseCase.validateProjectData(project)
        logRepository.addNewLog(
            LogEntry(
                userName = user.username,
                targetId = project.id,
                targetType = TargetType.PROJECT,
                action = "${project.id} Created Project",
                oldValue = "Project $project",
                newValue = "Project $project",
            )
        )
        return projectRepository.editProject(project)
    }
}


