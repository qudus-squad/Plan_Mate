package org.qudus.squad.ui.features.projects

class ProjectsControlScreen (
    private val projectsManagementRepository: ProjectsManagementRepository
) {

     suspend fun manageAllProjectsPanel() {
        println("┌──────────────────────────────┐")
        println("│        MANAGE PROJECTS       │")
        println("│──────────────────────────────│")
        println("│1- OPEN PROJECT BY ID         │")
        println("│2- DELETE PROJECT BY ID       │")
        println("│3- CREATE NEW PROJECT         │")
        println("│0- RETURN                     │")
        println("└──────────────────────────────┘")
        suspend fun looop() {
            when (readlnOrNull()?.trim()) {
                "1" -> projectsManagementRepository.viewProjectById()
                "2" -> projectsManagementRepository.deleteProject()
                "3" -> projectsManagementRepository.createNewProject()
                "0" -> return
                else -> {
                    println("invalid try again ")
                    looop()
                }
            }

        }
        looop()
    }
    suspend fun managePanel(){
        println("┌──────────────────────────────┐")
        println("│        MANAGE PROJECTS       │")
        println("│──────────────────────────────│")
        println("│1- OPEN PROJECT BY ID         │")
        println("│0- RETURN                     │")
        println("└──────────────────────────────┘")

        suspend fun looop (){when (readlnOrNull()?.trim()) {
            "1" -> projectsManagementRepository.viewProjectById()
            "0" -> return
            else -> {
                println("invalid try again ")
                looop()
            }
        }

        }
        looop ()
    }
}