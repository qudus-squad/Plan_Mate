package org.qudus.squad.ui.controlPanel

import logic.use_cases.log.GetAllLogsUseCase
import logic.use_cases.project.GetAllProjectsUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.logic.model.User
import org.qudus.squad.logic.model.UserRole
import org.qudus.squad.ui.tablesDisplay.LogsTableDisplay
import org.qudus.squad.ui.tablesDisplay.ProjectsTableDisplay
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class MateControlPanel(private val user: User,
    private val taskManagement: TaskManagement ) {

    suspend fun mateStory() {
        if (user.role != UserRole.MATE) {
            return
        } else while (true) {
            println("ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ   PLAN MATE  ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ")
            println("┌───────────────────────────────────────────┐")
            println("              WELCOME '${user.username}'       ")
            println("│───────────────────────────────────────────│")
            println("│              1- ALL PROJECTS              │")
            println("│              2- RECENT LOGS               │")
            println("│              0- LOG OUT                   │")
            println("└───────────────────────────────────────────┘")
            when (readlnOrNull()?.trim()) {
                "1" -> getAllProjects()
                "2" -> getAllLogs()
                "0" -> return
            }
        }
    }

    ///////////////////////////// MANAGE PROJECTS ////////////////////////////// ( 0 - > 1 )

    suspend fun getAllProjects() {
        val display = ProjectsTableDisplay(dateFormater = DateTimeFormatter)
        val repository: ProjectRepository = getKoin().get()
        val getAllProjects = GetAllProjectsUseCase(repository)
        val allProjects = getAllProjects.getAllProjects()
        if (allProjects.isNotEmpty()) {
            display.displayProjectsTable(allProjects)
            manageProjectPanel()
        } else targetNotFound("PROJECT")
    }
    ///////////////////////////// RECENT LOGS ////////////////////////////// ( 0 - > 2 )

    private suspend fun getAllLogs() {
        val display = LogsTableDisplay(dateFormater = DateTimeFormatter)
        val repository: LogRepository = getKoin().get()
        val logEntryValidator: LogEntryDataValidationUseCase = getKoin().get()
        val getAllLogs = GetAllLogsUseCase(repository,logEntryValidator)
        val recentLogs = getAllLogs.getAllLogs()
        if (recentLogs.isNotEmpty()) {
            display.displayLogsDetails(recentLogs)
        } else targetNotFound("LOG")
    }

    ///////////////////////////// CONTROL PANELS //////////////////////////////

    private suspend fun manageProjectPanel(id :String = "") {//after displaying one project
        println("┌─────────────────────────────────────────┐")
        println("│1- CREATE NEW TASK  │ 4- DELETE TASK     │")
        println("│2- EDIT TASK NAME   │ 5- ASSIGN TO TASK  │")
        println("│3- EDIT TASK        │ 6- SWITCH TASK     │")
        println("│    DESCRIPTION     │  TO ANOTHER STATE  │")
        println("│                0- RETURN                │")
        println("└─────────────────────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> taskManagement.createNewTask(id)
            "2" -> taskManagement.editTaskNameUsingId()
            "3" -> taskManagement.editTaskDescriptionUsingId()
            "4" -> taskManagement.deleteTaskById(id)
            "5" -> taskManagement.assignTask()
            "6" -> taskManagement.switchTaskState()
            "0" -> return
        }

    }

    ///////////////////////////// ERRORS ////////////////////////////// ( 0 - > 3 )

    private fun targetNotFound(targetType: String) {
        println("┌${"─".repeat(30)}┐")
        println("│${"NO $targetType FOUND".center(30)}│")
        println("└${"─".repeat(30)}┘")
    }
}