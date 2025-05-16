package org.qudus.squad.ui.features.tasks

import logic.use_cases.tasks.*
import logic.use_cases.user.GetAllUsersUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.tasks.UnAssignTaskUseCase
import org.qudus.squad.logic.use_cases.user.GetUserByIdUseCase
import org.qudus.squad.model.entity.EditTaskInput
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.tablesDisplay.AllTasksTableDisplay
import org.qudus.squad.ui.tablesDisplay.AllUsersTableDisplay

class TasksManagementImplementation(
    private val tasksRepository: TaskRepository,
    private val loginSession: LoginSession,
    private val allTasksTableDisplay: AllTasksTableDisplay,
    private val allUsersTableDisplay: AllUsersTableDisplay,

) : TasksManagementRepository {
    private val editTask : EditTaskUseCase = getKoin().get()
    private val getTaskById : GetAllTasksByProjectIdUseCase = getKoin().get()
    private val deleteTask :  DeleteTaskUseCase= getKoin().get()
    private val unAssignTaskToUser : UnAssignTaskUseCase = getKoin().get()
   private val assignTaskToUser :AssignTaskToUserUseCase= getKoin().get()
   private val getAllUsers : GetAllUsersUseCase=getKoin().get()
    private val getUserById : GetUserByIdUseCase = getKoin().get()


    override suspend fun createNewTask(id: String) {
        val createNewTask: CreateNewTaskUseCase = getKoin().get()
        println("ENTER TASK NAME : ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER TASK DESCRIPTION : ")
        val descriptionSelected = readlnOrNull()?.trim() ?: ""
        println("ENTER TASK STATE NAME : ")
        val taskStateNameSelected = readlnOrNull()?.trim() ?: ""
        createNewTask.createNewTask(
            userName = loginSession.currentUser.username, task = Task(
                title = titleSelected,
                creatorUserID = loginSession.currentUser.userId,
                description = descriptionSelected,
                projectId = id,
                taskState = TaskState(name = taskStateNameSelected),))
        println("TASK '${titleSelected}' CREATED SUCCESSFULLY.")
        println(" ' 1 ' to continuew enter to exit 0 return ")
        return
    }
/////////////////////////////////////////////////////////////////////////////

    override suspend fun editTaskNameUsingId(id: String) {
        val projectTasks = getTaskById.getAllTasksByProjectId(id)
            allTasksTableDisplay.invoke(projectTasks)
            println("ENTER TASK ID : ")

        val selectedIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)
        if (selectedIndex != null && selectedIndex in projectTasks.indices) {
            val oldTask = tasksRepository.getTaskById(projectTasks[selectedIndex].id)
            println("ENTER NEW TITLE : ")
            val titleSelected = readlnOrNull()?.trim() ?: ""
            editTask.editTask(
                EditTaskInput(
                    userName = loginSession.currentUser.username,
                    updatedTask = oldTask.copy(title = titleSelected),
                    action = "edit Task Name form ${oldTask.title} to $titleSelected",
                    oldValue = oldTask.title,
                    newValue = titleSelected,
                )
            )
            println("Task edited task name Successfully")
            println(" ' 1 ' to continuew enter to exit 0 return ")
            return
            }else println()
    }


    override suspend fun editTaskDescriptionUsingId(id: String)  {
        val projectTasks = getTaskById.getAllTasksByProjectId(id)
        allTasksTableDisplay.invoke(projectTasks)
        println("ENTER TASK ID : ")
        val selectedIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)
        if (selectedIndex != null && selectedIndex in projectTasks.indices) {
            val oldTask = tasksRepository.getTaskById(projectTasks[selectedIndex].id)
            println("ENTER NEW DESCRIPTION : ")
            val descriptionSelected = readlnOrNull()?.trim() ?: ""
            editTask.editTask(
                EditTaskInput(
                    userName = loginSession.currentUser.username,
                    updatedTask = oldTask.copy(description = descriptionSelected),
                    action = "edit Task description form ${oldTask.description} to $descriptionSelected",
                    oldValue = oldTask.description,
                    newValue = descriptionSelected,
                )
            )
            println("Task edited  Successfully")
            println(" ' 1 ' to continuew enter to exit 0 return ")
            return
        }else println()
    }
    ///////////////////////////////////////////////////////////////////////////////////

    override suspend fun deleteTaskById(id: String) {
         val user =loginSession.currentUser
        val projectTasks = getTaskById.getAllTasksByProjectId(id)
        allTasksTableDisplay.invoke(projectTasks)
        println("ENTER TASK ID : ")
        val selectedIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)
        if (selectedIndex != null && selectedIndex in projectTasks.indices) {
            val selectedTask = tasksRepository.getTaskById(projectTasks[selectedIndex].id)
            deleteTask.deleteTask(
                userName = user.username,
                taskId = selectedTask.id,
                taskTitle = selectedTask.title,
            )
                println("Task deleted successfully")
            println(" ' 1 ' to continuew enter to exit 0 return ")
            return
            }else println()
        }
    override suspend fun assignTask(id: String)  {
        val projectTasks = getTaskById.getAllTasksByProjectId(id)
        val allUsers = getAllUsers.getAllUsers()

        allTasksTableDisplay.invoke(projectTasks)
        println("ENTER TASK ID : ")
        val selectedIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)
        if (selectedIndex != null && selectedIndex in projectTasks.indices) {
            val selectedTask = tasksRepository.getTaskById(projectTasks[selectedIndex].id)


            allUsersTableDisplay.invoke(getAllUsers.getAllUsers())
            println("ENTER USER ID : ")
            val userIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)
            if (userIndex != null && selectedIndex in allUsers.indices) {
                val selectedUser = getUserById.getUserById(allUsers[userIndex].userId)

                assignTaskToUser.assignTaskToUser(userId = selectedUser.userId, taskId = selectedTask.id)
        println("Task state switched (unassigned) successfully.")
                println(" ' 1 ' to continuew enter to exit 0 return ")
                return
    }
        }else println()
    }
    override suspend fun unAssignTaskToUser(id: String) {
        val projectTasks = getTaskById.getAllTasksByProjectId(id)
        allTasksTableDisplay.invoke(projectTasks)
        println("ENTER TASK ID : ")
        val taskIdSelected = readlnOrNull()?.trim() ?: ""
            unAssignTaskToUser.unAssignTask(taskId = taskIdSelected)
            println("Task state switched (unassigned) successfully.")
    }
}

