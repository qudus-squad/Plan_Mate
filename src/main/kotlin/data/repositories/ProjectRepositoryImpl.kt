package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project


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

    override fun editProject(project: Project) {
        TODO("Not yet implemented")
    }
}
