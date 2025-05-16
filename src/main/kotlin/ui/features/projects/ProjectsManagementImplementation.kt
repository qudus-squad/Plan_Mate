package org.qudus.squad.ui.features.projects

import logic.use_cases.project.CreateNewProjectUseCase
import logic.use_cases.project.DeleteProjectUseCase
import logic.use_cases.project.GetAllProjectsUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.use_cases.project.GetProjectByIdUseCase
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.features.tasks.TasksControlScreen
import org.qudus.squad.ui.tablesDisplay.AllProjectsTableDisplay
import org.qudus.squad.ui.tablesDisplay.ProjectDetailsDisplay

class ProjectsManagementImplementation(
    private val allProjectsTableDisplay :  AllProjectsTableDisplay,
    private val projectDetailsDisplay : ProjectDetailsDisplay,
    private val tasksControlScreen: TasksControlScreen,
    private val loginSession : LoginSession,
    //error display
) : ProjectsManagementRepository {

    val getAllProjects: GetAllProjectsUseCase = getKoin().get()
    val getProjectById: GetProjectByIdUseCase = getKoin().get()
    val createNewProject: CreateNewProjectUseCase = getKoin().get()


    override suspend fun displayGetAllProjects() {
        val control: ProjectsControlScreen = getKoin().get()
        val user = loginSession.currentUser
            val allProjects = getAllProjects.getAllProjects()
            if (allProjects.isNotEmpty()) {
                allProjectsTableDisplay.invoke(allProjects)
                if (user.role == UserRole.ADMIN){
                    control.manageAllProjectsPanel()
                }else control.managePanel()
            } else {
                println("PROJECT")
            }
    }

    override suspend fun viewProjectById() {
        val user = loginSession.currentUser
        val allProjects = getAllProjects.getAllProjects()
        println("ENTER PROJECT ID :")

        val selectedIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)
            if (selectedIndex != null && selectedIndex in allProjects.indices) {
                val selectedProject = getProjectById.getProjectById(allProjects[selectedIndex].id)

              suspend fun loop (){  while (true){
                    projectDetailsDisplay.invoke(selectedProject)
                if (user.role == UserRole.ADMIN) {
                    tasksControlScreen.manageOneProjectPanel(selectedProject.id)
                } else tasksControlScreen.manageProjectPanel(selectedProject.id)
             when (readlnOrNull()?.trim()) {
                 "1" -> loop()
                 "0" ->return
                 "" -> break}
                }

              }
                loop()
            }else println()
    }

    override suspend fun deleteProject() {
        val control: ProjectsControlScreen = getKoin().get()

        val projects = getAllProjects.getAllProjects()
        if (projects.isEmpty()) {
            println("noPROJECT")
            return //create new one ?!
        }
        println("ENTER PROJECT ID :")
        val selecteId = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)
        if (selecteId != null && selecteId in projects.indices) {
            val selectedProject = projects[selecteId]
            val deleteProject = getKoin().get<DeleteProjectUseCase>()
            deleteProject.deleteProject(loginSession.currentUser, selectedProject.id)
            println("PROJECT '${selectedProject.title}' DELETED SUCCESSFULLY.")
            displayGetAllProjects()
        } else {
            // idNotFound()
            println("PRESS ENTER TO TRY AGAIN OR 0 TO EXIT")
            if (readlnOrNull()?.trim() == "0") return control.manageAllProjectsPanel() else deleteProject()
        }
    }

    override suspend fun createNewProject() {
        val control: ProjectsControlScreen = getKoin().get()

        val user = loginSession.currentUser
        try {
            println("ENTER PROJECT NAME : ")
            val titleSelected = readlnOrNull()?.trim() ?: ""
            println("ENTER PROJECT DESCRIPTION : ")
            val descriptionSelected = readlnOrNull()?.trim() ?: ""
            createNewProject.createProject(
                user = user,
                title = titleSelected,
                description = descriptionSelected,
            )
            println("PROJECT '${titleSelected}' CREATED SUCCESSFULLY.")
            displayGetAllProjects()
        } catch (e: Exception) {
            println("FAILED TO CREATE PROJECT PRESS ENTER TO TRY AGAIN OR 0 TO EXIT ")
            if (readlnOrNull()?.trim() == "0") return control.manageAllProjectsPanel() else createNewProject()
        }

    }

}

//    private suspend fun editProject() {
//        val editProject:EditProjectUseCase = getKoin().get()
//        println("ENTER NEW NAME (OR LEAVE EMPTY FOR OLD VALUE): ")
//        val titleSelected = readlnOrNull()?.trim() ?: ""
//        println("ENTER NEW DESCRIPTION (OR LEAVE EMPTY FOR OLD VALUE): ")
//        val descriptionSelected = readlnOrNull()?.trim() ?: ""
//        if (editProject.editProject(
//                user = user,
//                project = Project(
//                    title = titleSelected,
//                    creatorUserId = user.userId,
//                    description = descriptionSelected,
//                )
//            )
//        ) {
//            println("project update")
//        } else {
//            println(" failed  Empty try again or  0 to exit ")
//            if (readlnOrNull()?.trim() == "0") return
//            editProject()
//        }
//    }

/*
///////////////////////////// ERRORS ////////////////////////////// ( 0 - > 3 )
private fun targetNotFound(targetType: String) {
    println("┌${"─".repeat(30)}┐")
    println("│${"NO $targetType FOUND".center(30)}│")
    println("└${"─".repeat(30)}┘")
}

private fun idNotFound() {
    println("┌───────────────────────────────┐")
    println("│      INVALID ID TRY AGAIN     │")
    println("└───────────────────────────────┘")
}
}
*/
