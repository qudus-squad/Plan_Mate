package org.qudus.squad.ui.controlPanel

import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.ui.features.logs.LogsManagementRepository
import org.qudus.squad.ui.features.projects.ProjectsManagementRepository
import org.qudus.squad.ui.features.users.UsersManagementRepository

class AdminControlPanel (
    private val loginSession: LoginSession,
    private val usersManagementRepository: UsersManagementRepository,
    private val projectsManagementRepository: ProjectsManagementRepository,
    private val logsManagementRepository: LogsManagementRepository
) {
    suspend fun adminStory() {
        val user = loginSession.currentUser
        while (true) {
            println("ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ   PLAN MATE  ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ")
            println("┌───────────────────────────────────────────┐")
            println("           WELCOME '${user.username}'        ")
            println("│───────────────────────────────────────────│")
            println("│              1- MANAGE PROJECTS           │")
            println("│              2- MANAGE USERS              │")
            println("│              3- RECENT LOGS               │")
            println("└───────────────────────────────────────────┘")
            when (readlnOrNull()?.trim()) {
                "1" -> projectsManagementRepository.displayGetAllProjects()
                "2" -> usersManagementRepository.getAllUsers()
                "3" -> logsManagementRepository.recentLogsScreen()
                else -> invalidOption()
            }
        }
    }

    private fun invalidOption() {
        println("┌───────────────────────────────┐")
        println("│   INVALID OPTION TRY AGAIN    │")
        println("└───────────────────────────────┘")
    }

}