package org.qudus.squad.data.data_source.project_data_source

import org.qudus.squad.model.entity.Project

interface ProjectDataSource {
    fun getAllProjects(): List<Project>
    fun deleteProjectById(id: String)
    fun createNewProject(project: Project)
    fun getProjectById(id: String): Project
    fun editProject(project: Project)
}