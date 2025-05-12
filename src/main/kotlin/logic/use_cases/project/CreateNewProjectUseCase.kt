package logic.use_cases.project

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.UserRoleValidationUseCase
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.Project
import org.qudus.squad.logic.model.TargetType
import org.qudus.squad.logic.model.Task
import org.qudus.squad.logic.model.TaskState
import org.qudus.squad.logic.model.User

class CreateNewProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val projectDataValidationUseCase: ProjectDataValidationUseCase,
    private val logRepository: LogRepository,
    private val userRoleValidationUseCase: UserRoleValidationUseCase
) {
    suspend fun createProject(
        user: User,
        title: String,
        description: String,
        tasks: List<Task> = emptyList(),
        tasksState: List<TaskState> = emptyList()
    ): Project {

        userRoleValidationUseCase.checkUserRoleIsAdmin(user.role)

        val newProjectInfo = Project(
            id = GenerateUUID().generate(),
            title = title,
            creatorUserId = user.userId,
            description = description,
            tasks = tasks,
            taskState = tasksState
        )

        projectDataValidationUseCase.validateProjectData(newProjectInfo)
        val createdProject = projectRepository.createNewProject(newProjectInfo)
        logCreateNewProject(user, createdProject)

        return createdProject
    }

    private suspend fun logCreateNewProject(
        user: User,
        createdProject: Project,
    ) {
        logRepository.addNewLog(
            LogEntry(
                userName = user.username,
                targetId = createdProject.id,
                targetType = TargetType.PROJECT,
                action = "Project with id ${createdProject.id} Created",
                newValue = "title ${createdProject.title} | description ${createdProject.description}",
            )
        )
    }

}