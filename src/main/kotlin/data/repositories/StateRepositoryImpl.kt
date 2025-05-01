package org.qudus.squad.data.repositories

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.State

class StateRepositoryImpl : StateRepository {
    override fun addStateToProject(projectId: String, state: State): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun editState(projectId: String, oldState: State, modifiedState: State): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteState(projectId: String, state: State): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getAllStatesForProject(projectId: String): List<State> {
        TODO("Not yet implemented")
    }
}