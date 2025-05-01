package org.qudus.squad.logic.repositories

import org.qudus.squad.model.Project

interface ProjectRepository {
    fun createNewProject(project: Project): Project
}