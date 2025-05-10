package org.qudus.squad.ui.controlPanel

import logic.use_cases.log.GetAllLogsUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.User
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
        val repository: ProjectRepository = getKoin().get()

        val getAllProjects = GetAllProjectsUseCase(repository)
        val lisOfProjectsId =  getAllProjects.getAllProjects().map { it.id }
        val display = OneProjectTableDisplay()
        val getProjectById = GetProjectByIdUseCase(repository)
        println("ENTER PROJECT ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        if (idSelected in lisOfProjectsId){
        display.main()
        manageOneProjectPanel()}
       else idNotFound()
    }

    private suspend fun deleteProject() {
        val repository: ProjectRepository = getKoin().get()

        val getAllProjects = GetAllProjectsUseCase(repository)
        val lisOfProjectsId =  getAllProjects.getAllProjects().map { it.id }
        val logeRepository: LogRepository = getKoin().get()
        val deleteProject = DeleteProjectUseCase(repository, logeRepository)
        println("ENTER PROJECT ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        if (idSelected in lisOfProjectsId){
        deleteProject.deleteProject(user, idSelected)
        println("PROJECT WITH : '$idSelected' ID DELETED")}
         else idNotFound()
    }

    suspend fun createNewProject() {
        val repository: ProjectRepository = getKoin().get()
        val validation: ProjectDataValidationUseCase = getKoin().get()
        val logeRepository: LogRepository = getKoin().get()
        val createNewProject = CreateNewProjectUseCase(repository, validation, logeRepository)
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
            //tasks =listOfTasksSelected ,
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

    private suspend fun deleteUser() {
        val deleteUserUseCase: DeleteUserUseCase = getKoin().get()
        println("ENTER USER ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        deleteUserUseCase.deleteUser(user.username, idSelected)
        println("USER WITH : '$idSelected' ID DELETED")
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