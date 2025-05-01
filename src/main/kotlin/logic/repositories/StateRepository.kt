package org.qudus.squad.logic.repositories

import org.qudus.squad.model.State

interface StateRepository {
    fun addStateToProject(projectId: String, state: State): Result<Unit>
    fun editState(projectId: String, oldState: State, modifiedState: State): Result<Unit>
    fun deleteState(projectId: String, state: State): Result<Unit>
    fun getAllStatesForProject(projectId: String): List<State>
}