package fakes

import logic.exceptions.ProjectNotFoundException
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource.Companion.PROJECT_NOT_FOUND
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project

class FakeProjectRepository : ProjectRepository {

    private val projects = mutableListOf<Project>()

    override suspend fun getAllProjects(): List<Project> {
        return projects.toList()
    }

    override suspend fun deleteProjectById(id: String): Boolean {

        val allProjects = getAllProjects()

        val isProjectExists = allProjects.firstOrNull { it.id == id }
        if (isProjectExists == null) return false

        projects.removeIf { it.id == id }
        return true
    }

    override suspend fun createNewProject(project: Project): Project {
        projects.add(project)
        return project
    }

    override suspend fun getProjectById(id: String): Project {
        return projects.find { it.id == id } ?: throw ProjectNotFoundException(PROJECT_NOT_FOUND)
    }

    override suspend fun editProject(project: Project): Boolean {
        val index = projects.indexOfFirst { it.id == project.id }
        return if (index != -1) {
            projects[index] = project
            true
        } else {
            false
        }
    }
}