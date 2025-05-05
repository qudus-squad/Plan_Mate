package fakes

import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project

class FakeProjectRepository : ProjectRepository {

    private val projects = mutableListOf<Project>()

    override fun getAllProjects(): List<Project> {
        return projects.toList()
    }

    override fun deleteProjectById(id:String):Boolean {
        return projects.removeIf { it.id == id }
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