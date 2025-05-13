package org.qudus.squad.ui.controlPanel

import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskStateRepository
import org.qudus.squad.ui.tablesDisplay.TaskStateDisplay

class StateManagement() {

    suspend fun createNewState(id: String) {
        val repository: TaskStateRepository = getKoin().get()
        val logRepository: LogRepository = getKoin().get()
    /*  // val createNewState = CreateNewStatekUseCase(
            repository,
            logRepository = logRepository,
        )*/
        println("ENTER STATE NAME : ")
        val titleSelected = readlnOrNull()?.trim() ?: ""
    }

    suspend fun deleteStateById(id: String) {
        val repository: TaskStateRepository = getKoin().get()
        val display = TaskStateDisplay()
       // val states = getAllStates.
      //  display.displayStatesTable(states)
        val logRepository: LogRepository = getKoin().get()
      //  val deleteState = DeleteTaskStateUseCase(
       // )
        println("ENTER STATE ID : ")
        val idSelected = readlnOrNull()?.trim() ?: ""
      //  val task = tasksRepository.getTaskById(idSelected)
       // if (state != null) {
            println("STATE  DELETED successfully")
         ///   deleteState .deleteTaskSTATE(
         //       user.username, taskId = idSelected, taskTitle = task.title
            //)
        }// else{ println("No task found with ID : $idSelected") }

    }

//}
