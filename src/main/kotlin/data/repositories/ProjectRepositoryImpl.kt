package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.Project

class ProjectRepositoryImpl(private val projectDataSource: ProjectDataSource) : ProjectRepository {

    override fun getAllProjects(): List<Project> {
        return projectDataSource.getAllProjects()
    }

    override fun deleteProjectById(id: String) {
        return projectDataSource.deleteProjectById(id)
    }

}