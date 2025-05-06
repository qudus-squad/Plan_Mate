package org.qudus.squad.logic.repositories

import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Project

interface ProjectRepository {
    fun getAllProjects(): List<Project>
    fun deleteProjectById(id: String = GenerateUUID().generate()): Boolean
    fun createNewProject(project: Project): Project
    fun getProjectById(id: String = GenerateUUID().generate()): Project?
    fun editProject(project: Project): Boolean
}