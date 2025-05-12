package org.qudus.squad.data.data_source.project_data_source

import org.qudus.squad.logic.model.Project

interface ProjectDataSource {
    suspend fun getAllProjects(): List<Project>
    suspend fun deleteProjectById(id: String): Boolean
    suspend fun createNewProject(project: Project): Project
    suspend fun getProjectById(id: String): Project
    suspend fun editProject(project: Project): Project
}