package org.qudus.squad.ui.features

import org.qudus.squad.logic.usecases.project.CreateNewProjectUseCase
import org.qudus.squad.model.State
import org.qudus.squad.model.User
import org.qudus.squad.model.UserRole

class ManageProjectsUI(
    private val createNewProjectUseCase: CreateNewProjectUseCase
) {
    fun mangeProjects(user: User) {
        if (user.role != UserRole.ADMIN) {
            return
        }
        while (true) {
            println("\n=== Manage Projects ===")
            println("1- Create New Project")
            println("2- Delete Project")
            println("3- Edit Project")
            when (readlnOrNull()?.trim()) {
                "1" -> createNewProject(user)
            }
        }
    }

    private fun createNewProject(user: User) {
        println(" Enter Project Name")
        val titleEntered = readlnOrNull()?.trim() ?: ""
        val title = titleEntered.toString()

        println("Enter project Description")
        val descriptionEntered = readlnOrNull()?.trim() ?: ""
        val description = descriptionEntered.toString()

        println("Enter States (separated By Comma Character(,) )")

        val statesEntered = readlnOrNull()?.trim()?.split(",") ?: emptyList()
        val states = statesEntered.map { string -> State(name = string) }

        val projectToBeCreated = createNewProjectUseCase.createProject(
            title = title,
            description = description,
            state = states,
            user = user
        )
        println(" user : ${projectToBeCreated.creatorUserId} created ${projectToBeCreated.title} ")
    }
}