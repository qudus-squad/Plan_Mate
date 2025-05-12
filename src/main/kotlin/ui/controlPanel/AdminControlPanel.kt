package org.qudus.squad.ui.controlPanel

import logic.use_cases.log.GetAllLogsUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.logic.model.User
import org.qudus.squad.ui.controlPanel.admin.ManageProject
import org.qudus.squad.ui.controlPanel.admin.ManageUsers
import org.qudus.squad.ui.tablesDisplay.LogsTableDisplay
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class AdminControlPanel (
    private val user: User,
    private val manageProject: ManageProject,
    private val manageUsers:ManageUsers
) {
    suspend fun adminStory() {
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
                "1" -> manageProject.getAllProjects()
                "2" -> manageUsers.getAllUsers()
                "3" -> recentLogsScreen()
                else -> invalidOption()
            }
        }
    }

    ///////////////////////////// RECENT LOGS ////////////////////////////// ( 0 - > 3 )

    private suspend fun recentLogsScreen() {
        val display = LogsTableDisplay(dateFormater = DateTimeFormatter)
        val repository: LogRepository = getKoin().get()
        val logEntryValidator: LogEntryDataValidationUseCase= getKoin().get()
        val getAllLogs = GetAllLogsUseCase(repository,logEntryValidator)
        val recentLogs = getAllLogs.getAllLogs()
        if (recentLogs.isNotEmpty()) {
            display.displayLogsDetails(recentLogs)
        } else targetNotFound("LOG")
        when (readlnOrNull()?.trim()) {
            else -> adminStory()
        }
    }


    ///////////////////////////// ERRORS ////////////////////////////// ( 0 - > 3 )

    private fun targetNotFound(targetType: String) {
        println("┌${"─".repeat(30)}┐")
        println("│${"NO $targetType FOUND".center(30)}│")
        println("└${"─".repeat(30)}┘")
    }

    private fun invalidOption() {
        println("┌───────────────────────────────┐")
        println("│   INVALID OPTION TRY AGAIN    │")
        println("└───────────────────────────────┘")
    }
}