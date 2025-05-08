package org.qudus.squad.ui.controlPanel

import logic.use_cases.log.GetAllLogsUseCase
import logic.use_cases.project.CreateNewProjectUseCase
import logic.use_cases.project.DeleteProjectUseCase
import logic.use_cases.project.GetAllProjectsUseCase
import logic.use_cases.user.AddNewUserUseCase
import logic.use_cases.user.GetAllUsersUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.use_cases.project.GetProjectByIdUseCase
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.tablesDisplay.LogsTableDisplay
import org.qudus.squad.ui.tablesDisplay.OneProjectTableDisplay
import org.qudus.squad.ui.tablesDisplay.ProjectsTableDisplay
import org.qudus.squad.ui.tablesDisplay.UsersTableDisplay
import org.qudus.squad.ui.utils.DataHashing
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class AdminControlPanel(private val user: User) {

    suspend fun adminStory() {
        if (user.role != UserRole.ADMIN) {
            return
        } else
            while (true) {
                println("ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ   PLAN MATE  ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ")
                println("┌───────────────────────────────────────────┐")
                println("           WELCOME   ${user.username}        ")
                println("│───────────────────────────────────────────│")
                println("│              1- MANAGE PROJECTS           │")
                println("│              2- MANAGE USERS              │")
                println("│              3- RECENT LOGS               │")
                println("│              0- LOG OUT                   │")
                println("└───────────────────────────────────────────┘")
                when (readlnOrNull()?.trim()) {
                    "1" -> manageProjectScreen()
                    "2" -> manageUsersScreen()
                    "3" -> recentLogsScreen()
                }
            }
    }

    ///////////////////////////// MANAGE PROJECTS ////////////////////////////// ( 0 - > 1 )

    private suspend fun manageProjectScreen() {
        println("ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ   PLAN MATE  ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ")
        println("┌───────────────────────────────────────────┐")
        println("│               MANAGE PROJECTS             │")
        println("│───────────────────────────────────────────│")
        println("│              1- ALL PROJECTS              │")
        println("│              2- DELETE PROJECT BY ID      │")
        println("│              3- CREATE NEW PROJECT        │")
        println("│              0- RETURN                    │")
        println("└───────────────────────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> getAllProjects() //DONE
            "2" -> deleteProject() //DONE
            "3" -> createNewProject()
            "0" -> return
        }
    }

    suspend fun getAllProjects() {
        val display = ProjectsTableDisplay(dateFormater = DateTimeFormatter)
        val repository: ProjectRepository = getKoin().get()
        val getAllProjects = GetAllProjectsUseCase(repository)
        val allProjects = getAllProjects.getAllProjects()
        if (allProjects.isNotEmpty()) {
            display.displayProjectsTable(allProjects)
            manageAllProjectsPanel()
        } else targetNotFound("PROJECT")
        return
    }

    private suspend fun viewProjectById() {
        val display = OneProjectTableDisplay()
        val repository: ProjectRepository = getKoin().get()
        val getProjectById = GetProjectByIdUseCase(repository)
        println("ENTER PROJECT ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        // if (idSelected in allProjects )
        val selectedProject = getProjectById.getProjectById(idSelected)
        display.displayProjectDetail(selectedProject)
        manageOneProjectPanel()
        // else{
        idNotFound()
        return
    }

    private suspend fun deleteProject() {
        val repository: ProjectRepository = getKoin().get()
        val deleteProject = DeleteProjectUseCase(repository)
        println("ENTER PROJECT ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        // if (idSelected in allProjects )
        deleteProject.deleteProject(idSelected)
        println("PROJECT WITH : '$idSelected' ID DELETED")
        // else{
        idNotFound()
        return
    }

    suspend fun createNewProject() {
        val repository: ProjectRepository = getKoin().get()
        val validation: ProjectDataValidationUseCase = getKoin().get()
        val createNewProject = CreateNewProjectUseCase(repository, validation)
        println("ENTER PROJECT NAME : ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER PROJECT DESCRIPTION : ")
        val descriptionSelected = readlnOrNull()?.trim() ?: ""
        println("Enter States (separated By Comma Character',')")
        val statesEntered = readlnOrNull()?.trim()?.split(",") ?: emptyList()
        val taskStates = statesEntered.map { string -> TaskState(name = string) }
        val listOfTasksSelected = readlnOrNull()?.trim() ?: "".toList()
        createNewProject.createProject(
            user = user,
            title = titleSelected,
            description = descriptionSelected,
            //  tasks =tasksSelected ,
        )
    }

    ///////////////////////////// MANAGE USERS ///////////////////////////// ( 0 - > 2 )

    private suspend fun manageUsersScreen() {
        println("ΞΞΞΞΞΞΞΞΞΞΞΞΞΞ   PLAN MATE  ΞΞΞΞΞΞΞΞΞΞΞΞΞΞ")
        println("┌─────────────────────────────────────────┐")
        println("│               MANAGE USERS              │")
        println("│─────────────────────────────────────────│")
        println("│              1- ALL USERS               │")
        println("│              2- CREATE NEW USER         │")
        println("│              3- DELETE USER BY ID       │")
        println("│              0- RETURN                  │")
        println("└─────────────────────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> getAllUsers()
            "2" -> createNewUser()
            "3" -> deleteUser() //add use case
        }
    }

    private suspend fun getAllUsers() {
        val display = UsersTableDisplay()
        val repository: UserRepository = getKoin().get()
        val getAllUsers = GetAllUsersUseCase(repository)
        val allUsers = getAllUsers.getAllUsers()
        if (allUsers.isNotEmpty()) {
            display.displayUsers(allUsers)
            manageUsersPanel()
        } else targetNotFound("USER")
        return
    }

    private suspend fun createNewUser() {
        val repository: UserRepository = getKoin().get()
        val validation: UserDataValidationUseCase = getKoin().get()
        val hashing: DataHashing = getKoin().get()
        val createNewUser = AddNewUserUseCase(repository, validation, hashing)
        println("ENTER USER NAME : ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER USER PASSWORD : ")
        val passwordSelected = readlnOrNull()?.trim() ?: ""
        println("SELECT USER ROLE")
        println("1.ADMIN")
        println("2.MATE")
        val roleChoice = readlnOrNull()?.trim() ?: ""
        val selectedRole = when (roleChoice) {
            "1" -> UserRole.ADMIN
            else -> UserRole.MATE
        }
        createNewUser.addUser(
            currentUserRole = user.role,
            username = titleSelected,
            password = passwordSelected,
            userRole = selectedRole,
        )
    }

    private fun deleteUser() {
        val repository: UserRepository = getKoin().get()
        // val deleteUser = DeleteUserUseCase(repository)
        println("ENTER USER ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        // if (idSelected in all users )
        // deleteUser.deleteUser(idSelected)
        println("USER WITH : '$idSelected' ID DELETED")
        // else{
        idNotFound()
        return
    }

    ///////////////////////////// RECENT LOGS ////////////////////////////// ( 0 - > 3 )

    private suspend fun recentLogsScreen() {
        val display = LogsTableDisplay(dateFormater = DateTimeFormatter)
        val repository: LogRepository = getKoin().get()
        val getAllLogs = GetAllLogsUseCase(repository)
        val recentLogs = getAllLogs.getAllLogs()
        if (recentLogs.isNotEmpty()) {
            display.displayLogsDetails(recentLogs)
        } else targetNotFound("LOG")
        return
    }

    ///////////////////////////// CONTROL PANELS //////////////////////////////

    private suspend fun manageUsersPanel() {// after displaying all users
        println("┌───────────────────────────┐")
        println("│         MANAGE USERS      │")
        println("│───────────────────────────│")
        println("│1- EDIT USER BY NAME OR ID │")
        println("│2- DELETE USER             │")
        println("│3- CREATE NEW USER         │")
        println("│0- RETURN                  │")
        println("└───────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> viewProjectById()
            "2" -> deleteProject()
            "3" -> createNewProject()
            "0" -> return
        }
    }

    private suspend fun manageAllProjectsPanel() { // after displaying all projects
        println("┌──────────────────────────────┐")
        println("│        MANAGE PROJECTS       │")
        println("│──────────────────────────────│")
        println("│1- OPEN PROJECT BY ID         │")
        println("│2- DELETE PROJECT BY ID       │")
        println("│3- CREATE NEW PROJECT         │")
        println("│0- RETURN                     │")
        println("└──────────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> viewProjectById()
            "2" -> deleteProject()
            "3" -> createNewProject()
            "0" -> return
        }
    }

    private suspend fun manageOneProjectPanel() { // after displaying one project
        println("┌───────────────────────────────────────────┐")
        println("│                  PROJECT                  │")
        println("│           1- EDIT PROJECT NAME            │")
        println("│          2- EDIT PROJECT DESCRIPTION      │")
        println("│───────────────────────────────────────────│")
        println("│       TASKS                  STATES       │")
        println("│3- CREATE NEW TASK  │ 8 - CREATE NEW STATE │")
        println("│4- DELETE TASK      │ 9 - DELETE STATE     │")
        println("│5- EDIT TASK NAME   │ 10- EDIT STATE NAME  │")
        println("│6- EDIT DESCRIPTION │ 11- SWITCH TASK TO   │")
        println("│7- ASSIGN TASK      │     ANOTHER STATE    │")
        println("│                0- RETURN                  │")
        println("└───────────────────────────────────────────┘")
        /*when (readlnOrNull()?.trim()) {
            "1" -> editProject()
            "2" -> editproject()
            "3" -> createNewTask()
            "4" -> deleteTaskById()
            "5" -> editTask()
            "6" -> editTask()
            "7" -> assignTask()
            "8" -> createNewState()
            "9" -> deleteState()
            "10"-> editState()
            "11"-> switchTaskState()
            "0" -> return
            else -> println("INVALID OPTION")
            }*/
    }
    ///////////////////////////// ERRORS ////////////////////////////// ( 0 - > 3 )

    private fun targetNotFound(targetType: String) {
        println("┌${"─".repeat(30)}┐")
        println("│${"NO $targetType FOUND".center(30)}│")
        println("└${"─".repeat(30)}┘")
    }
    private fun idNotFound() {
        println("┌───────────────────────────────┐")
        println("│      INVALID ID TRY AGAIN     │")
        println("│               OR              │")
        println("│       ENTER 0 TO RETURN       │")
        println("└───────────────────────────────┘")
        //when(readlnOrNull()?.trim())
    }
}