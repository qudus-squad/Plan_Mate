package org.qudus.squad.ui.controlPanel.admin

import logic.use_cases.project.CreateNewProjectUseCase
import logic.use_cases.project.DeleteProjectUseCase
import logic.use_cases.project.EditProjectUseCase
import logic.use_cases.project.GetAllProjectsUseCase
import logic.use_cases.tasks.GetAllTasksByProjectIdUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.TaskCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.data.data_source.task_data_source.CsvTaskDataSource
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.project.GetProjectByIdUseCase
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.ui.controlPanel.TaskManagement
import org.qudus.squad.ui.tablesDisplay.OneProjectTableDisplay
import org.qudus.squad.ui.tablesDisplay.ProjectsTableDisplay
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class ManageProject(
    private val user: User,
    private val taskManagement: TaskManagement
) {


    ///////////////////////////// MANAGE PROJECTS ////////////////////////////// ( 0 - > 1 )

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
        val getAllProjects: GetAllProjectsUseCase = getKoin().get()
        val lisOfProjectsId = getAllProjects.getAllProjects().map { it.id }
        val display = OneProjectTableDisplay()

        val getProjectById: GetProjectByIdUseCase = getKoin().get()
        println("ENTER PROJECT ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        if (idSelected in lisOfProjectsId) {
            display.displayProjectDetail(getProjectById.getProjectById(idSelected))
            manageOneProjectPanel(idSelected)
        } else idNotFound()
    }

    private suspend fun deleteProject() {
        val getAllProjects: GetAllProjectsUseCase = getKoin().get()
        val lisOfProjectsId = getAllProjects.getAllProjects().map { it.id }
        val deleteProject: DeleteProjectUseCase = getKoin().get()
        println("ENTER PROJECT ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
        if (idSelected in lisOfProjectsId) {
            deleteProject.deleteProject(user, idSelected)
            println("PROJECT WITH : '$idSelected' ID DELETED")
            getAllProjects()
        } else idNotFound()
        println("PRESS ENTER TO TRY AGAIN OR 0 TO EXIT ")
        if (readlnOrNull()?.trim() == "0") return manageAllProjectsPanel() else deleteProject()
    }

    suspend fun createNewProject() {
        try {

            val createNewProject: CreateNewProjectUseCase = getKoin().get()
            println("ENTER PROJECT NAME : ")
            val titleSelected = readlnOrNull()?.trim() ?: ""
            println("ENTER PROJECT DESCRIPTION : ")
            val descriptionSelected = readlnOrNull()?.trim() ?: ""
            println("Enter States (separated By Comma Character',')")
            val statesEntered = readlnOrNull()?.trim()?.split(",") ?: emptyList()
            println("Enter States (separated By Comma Character',')")
            val tasks = listOf(
                Task(
                    title = "Design ",
                    description = "dd1",
                    creatorUserID = "",
                    projectId = "1",
                    taskState = TaskState(name = "IN PROGRESS")
                ),
                Task(
                    title = "Create ",
                    description = "",
                    creatorUserID = "",
                    projectId = "1",
                    taskState = TaskState(name = "IN PROGRESS")
                ),
                Task(
                    title = "Implement ",
                    description = "",
                    creatorUserID = "",
                    projectId = "1",
                    taskState = TaskState(name = "IN PROGRESS")
                ),
                Task(
                    title = "tests",
                    description = "",
                    creatorUserID = "",
                    projectId = "1",
                    taskState = TaskState(name = "DONE")
                ),
                Task(
                    title = "apply",
                    description = "",
                    creatorUserID = "",
                    projectId = "1",
                    taskState = TaskState(name = "DONE")
                ),
                Task(
                    title = "save",
                    description = "",
                    creatorUserID = "",
                    projectId = "1",
                    taskState = TaskState(name = "TODO")
                ),
                Task(
                    title = "Review",
                    description = "",
                    creatorUserID = "",
                    projectId = "1",
                    taskState = TaskState(name = "TODO")
                )
            )
            val taskStates = statesEntered.map { string -> TaskState(name = string) }
            createNewProject.createProject(
                user = user,
                title = titleSelected,
                description = descriptionSelected,
                tasks = tasks,
            )
        } catch (e: Exception) {
            println("FAILED TO CREATE PROJECT PRESS ENTER TO TRY AGAIN OR 0 TO EXIT ")
            if (readlnOrNull()?.trim() == "0") return manageAllProjectsPanel() else createNewProject()
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

    private suspend fun manageAllProjectsPanel() {
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
    ///////////////////////////// CONTROL PANELS //////////////////////////////


    private suspend fun manageOneProjectPanel(id: String) { // after displaying one project
        println("┌───────────────────────────────────────────┐")
        println("│                  PROJECT                  │")
        println("│              1- EDIT PROJECT              │")
        println("│───────────────────────────────────────────│")
        println("│       TASKS        │       STATES         │")
        println("│2- CREATE NEW TASK  │6- CREATE NEW STATE   │")
        println("│3- DELETE TASK      │7- DELETE STATE       │")
        println("│4- EDIT TASK        │8- EDIT STATE         │")
        println("│5- ASSIGN TASK      │                      │")
        println("│───────────────────────────────────────────│")
        println("│   0- RETURN        │  EMPTY - MAIN MENU   │")
        println("└───────────────────────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> getTasksByProjectId()
            "2" -> taskManagement.createNewTask(id)
            "3" -> taskManagement.deleteTaskById(id)
            "4" -> taskManagement.editTaskNameUsingId()
            "5" -> taskManagement.editTaskDescriptionUsingId()
            "6" -> taskManagement.assignTask()
            "7" -> taskManagement.switchTaskState()
            "0" -> getAllProjects()
            else -> return
        }
    }

    private suspend fun getTasksByProjectId() {

        val get:GetAllTasksByProjectIdUseCase = getKoin().get()
        println("ENTER ): ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
        val print = get.getAllTasksByProjectId(titleSelected)
        println(print)
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
        println("└───────────────────────────────┘")
    }
}