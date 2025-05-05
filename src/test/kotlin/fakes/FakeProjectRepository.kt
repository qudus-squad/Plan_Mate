package fakes

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project

class FakeProjectRepository : ProjectRepository {

    private val projects = mutableListOf<Project>()

    override fun getAllProjects(): List<Project> {
        return projects.toList()
    }

    override fun deleteProjectById(id: String): Boolean {

        val allProjects = getAllProjects()

        val isProjectExists = allProjects.firstOrNull { it.id == id }
        if (isProjectExists == null) return false

        projects.removeIf { it.id == id }
        return true
    }

    override fun createNewProject(project: Project): Project {
        projects.add(project)
        return project
    }

    override fun getProjectById(id: String): Project? {
        return projects.find { it.id == id }
    }

    override fun editProject(project: Project) {
        val index = projects.indexOfFirst { it.id == project.id }
        if (index != -1) {
            projects[index] = project
        }
    }
}