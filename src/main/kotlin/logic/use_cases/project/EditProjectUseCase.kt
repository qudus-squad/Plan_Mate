package logic.use_cases.project

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.TaskState

class EditProjectUseCase(
    private val projectRepository: ProjectRepository,
) {

    fun editProject(project: Project): Boolean {

        val projectsList = projectRepository.getAllProjects()

        if (isNotValidProjectsList(projectsList)) return false
        val updatedProject = applyProjectChanges(project)
        if (!isValidInput(updatedProject)) return false

        return updateProject(updatedProject)
    }

    private fun isNotValidProjectsList(projectsList: List<Project>): Boolean {
        return projectsList.isEmpty()
    }

    private fun applyProjectChanges(project: Project): Project {
        var updatedProject = project.copy()

        // Read and apply title
        val newTitle = readlnOrNull()?.takeIf { it.isNotBlank() }
        if (newTitle != null) {
            updatedProject = updatedProject.copy(title = newTitle)
        }

        // Read and apply description
        val newDescription = readlnOrNull()?.takeIf { it.isNotBlank() }
        if (newDescription != null) {
            updatedProject = updatedProject.copy(description = newDescription)
        }

        // Read and apply states
        val newStates = readStatesInput()
        if (newStates.isNotEmpty()) {
            updatedProject = updatedProject.copy(taskState = newStates)
        }

        // Update timestamp
        updatedProject = updatedProject.copy(
            lastUpdateAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )

        return updatedProject
    }

    private fun readStatesInput(): List<TaskState> {
        val states = mutableListOf<TaskState>()
        println("Enter states (ID,Name pairs). Enter empty ID to finish:")

        while (true) {
            val id = readlnOrNull()?.takeIf { it.isNotBlank() } ?: break
            val name = readlnOrNull()?.takeIf { it.isNotBlank() } ?: continue
            states.add(TaskState(id, name))
        }

        return states
    }

    private fun isValidInput(project: Project): Boolean {
        return project.title.isNotBlank() &&
                project.description.isNotBlank() &&
                project.taskState.isNotEmpty() &&
                project.taskState.all { it.id.isNotBlank() && it.name.isNotBlank() }
    }

    private fun updateProject(project: Project): Boolean {
        return try {
            projectRepository.editProject(project)
            true
        } catch (e: Exception) {
            false
        }
    }
}


