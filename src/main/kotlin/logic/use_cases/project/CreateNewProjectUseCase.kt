package logic.use_cases.project

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.model.entity.*

class CreateNewProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val projectDataValidationUseCase: ProjectDataValidationUseCase,
    private val logRepository: LogRepository,
) {
    suspend fun createProject(
        user: User,
        title: String,
        description: String,
        tasks: List<Task> = emptyList(),
        tasksState: List<TaskState> = emptyList()
    ): Project {

        user.checkUserIsAdmin()

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