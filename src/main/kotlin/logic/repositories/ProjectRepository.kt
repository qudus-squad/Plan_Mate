package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.Project

interface ProjectRepository {
    fun getAllProjects(): List<Project>
    fun deleteProjectById(id: String)
    fun createNewProject(project: Project): Project
    fun getProjectById(id: String): Project?
    fun editProject(project: Project)
}