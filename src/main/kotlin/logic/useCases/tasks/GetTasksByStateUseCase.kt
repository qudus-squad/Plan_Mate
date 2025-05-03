package org.qudus.squad.logic.useCases.tasks

import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.exceptions.UnknownException

class GetTasksByStateUseCase (private val getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase)   {

    fun filterProjectTasksByState (projectId: String , state: String) : List<Task> {
        val filteredTaskByState = getAllTasksByProjectIdUseCase.getAllTasksByProjectId(projectId)
            .filter{it.taskState.name == state}
            .ifEmpty { throw UnknownException("") }
return filteredTaskByState
    }
}