package org.qudus.squad.ui.controlPanel

import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.ui.features.logs.LogsManagementRepository
import org.qudus.squad.ui.features.projects.ProjectsManagementRepository
import org.qudus.squad.ui.features.tasks.TasksManagementRepository

class MateControlPanel(
    private val loginSession: LoginSession,
    private val projectsManagementRepository: ProjectsManagementRepository,
    private val logsManagementRepository: LogsManagementRepository,
    private val tasksManagementRepository: TasksManagementRepository
) {

    suspend fun mateStory() {
        val user = loginSession.currentUser
        while (true) {
            println("ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ   PLAN MATE  ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ")
            println("┌───────────────────────────────────────────┐")
            println("              WELCOME '${user.username}'       ")
            println("│───────────────────────────────────────────│")
            println("│              1- ALL PROJECTS              │")
            println("│              2- RECENT LOGS               │")
            println("└───────────────────────────────────────────┘")
            when (readlnOrNull()?.trim()) {
                "1" -> projectsManagementRepository.displayGetAllProjects()
                "2" -> logsManagementRepository.recentLogsScreen()
                "0" -> return
            }
        }
    }
}