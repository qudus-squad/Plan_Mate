package org.qudus.squad.logic.useCases.project

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.model.Project
import org.qudus.squad.model.State

class EditProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    private val projectsList = projectRepository.getAllProjects()

    fun editProject(project: Project): Boolean {
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
            updatedProject = updatedProject.copy(state = newStates)
        }

        // Update timestamp
        updatedProject = updatedProject.copy(
            lastUpdateAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )

        return updatedProject
    }

    private fun readStatesInput(): List<State> {
        val states = mutableListOf<State>()
        println("Enter states (ID,Name pairs). Enter empty ID to finish:")

        while (true) {
            val id = readlnOrNull()?.takeIf { it.isNotBlank() } ?: break
            val name = readlnOrNull()?.takeIf { it.isNotBlank() } ?: continue
            states.add(State(id, name))
        }

        return states
    }

    private fun isValidInput(project: Project): Boolean {
        return project.title.isNotBlank() &&
                project.description.isNotBlank() &&
                project.state.isNotEmpty() &&
                project.state.all { it.id.isNotBlank() && it.name.isNotBlank() }
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


