package org.qudus.squad.data.repositories

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.State


class ProjectRepositoryImpl(private val projectDataSource: ProjectDataSource) : ProjectRepository {

    override fun getAllProjects(): List<Project> {
        return projectDataSource.getAllProjects()
    }

    override fun deleteProjectById(id: String) {
        return projectDataSource.deleteProjectById(id)
    }

    override fun createNewProject(project: Project): Project {
        TODO("Not yet implemented")
    }

    override fun getProjectById(id: String): Project? {
        return getAllProjects().find { it -> it.id == id }
    }
    val project1 = Project(
        id = "P001",
        creatorUserId = "user123",
        title = "IoT Monitoring System",
        description = "Track sensors in real-time",
        state = listOf(State("1", "To Do")),
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        lastUpdateAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    )
    override fun editProject(project: Project) {
        return editProject(project)
    }

    override fun getProjectById(id: String): Project {
        TODO("Not yet implemented")
    }
}
