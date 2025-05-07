package org.qudus.squad.logic.repositories

import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Project

interface ProjectRepository {
    suspend fun getAllProjects(): List<Project>
    suspend fun deleteProjectById(id: String = GenerateUUID().generate()): Boolean
    suspend fun createNewProject(project: Project): Project
    suspend fun getProjectById(id: String): Project
    suspend fun editProject(project: Project): Boolean
}