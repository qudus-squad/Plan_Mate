package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.model.Project

class ProjectRepositoryImplementation(private val projectDataSource: ProjectDataSource) : ProjectRepository {

    override suspend fun getAllProjects(): List<Project> {
        return projectDataSource.getAllProjects()
    }

    override suspend fun deleteProjectById(id: String): Boolean {
        return projectDataSource.deleteProjectById(id)
    }

    override suspend fun createNewProject(project: Project): Project {
        return projectDataSource.createNewProject(project)
    }

    override suspend fun getProjectById(id: String): Project {
        return projectDataSource.getProjectById(id)
    }

    override suspend fun editProject(project: Project): Project {
        return projectDataSource.editProject(project)
    }
}