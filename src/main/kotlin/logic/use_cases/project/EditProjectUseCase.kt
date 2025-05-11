package logic.use_cases.project


import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.User


class EditProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val logRepository: LogRepository,
) {
    suspend fun editProject(user: User, project: Project): Project {
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


