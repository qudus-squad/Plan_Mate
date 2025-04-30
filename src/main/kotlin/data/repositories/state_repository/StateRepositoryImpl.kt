package org.qudus.squad.data.repositories.state_repository

import org.qudus.squad.logic.repositories.state_repository.StateRepository
import org.qudus.squad.model.State

class StateRepositoryImpl : StateRepository {
    override fun addStateToProject(projectId: String, stateName: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun editState(projectId: String, oldStateName: String, newStateName: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteState(projectId: String, stateName: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getAllStatesForProject(projectId: String): List<State> {
        TODO("Not yet implemented")
    }
}