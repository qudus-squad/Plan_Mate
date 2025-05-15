package logic.use_cases.project


import logic.exceptions.ProjectNotFoundException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.User


class EditProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository,
    private val userDataValidationUseCase: UserDataValidationUseCase,
    private val projectValidationUseCase: ProjectDataValidationUseCase
) {
    suspend fun editProject(user: User, project: Project): Project {
        userDataValidationUseCase.validateUserData(user.username, user.passwordHash)
        projectValidationUseCase.validateProjectData(project)
        val result = projectRepository.editProject(project)
        if(result) {
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
        }else
            throw ProjectNotFoundException(PROJECT_NOT_FOUND)
        return project
    }
    companion object{
        const val PROJECT_NOT_FOUND = "Project Not Found"

    }
}