package org.qudus.squad.logic.usecases.state

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.State

class GetAllStatesForProjectUseCase(private val stateRepository: StateRepository) {

    fun getAllStatesForProject(projectId: String): List<State> {

        return stateRepository.getAllStatesForProject(projectId = projectId)
    }
}