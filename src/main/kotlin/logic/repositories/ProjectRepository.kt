package org.qudus.squad.logic.repositories

import org.qudus.squad.model.Project

interface ProjectRepository {
    fun getAllProjects(): List<Project>
    fun deleteProjectById(id: String)
}