package org.qudus.squad.logic.repositories.state_repository

import org.qudus.squad.model.State

interface StateRepository {
    fun addStateToProject(projectId: String, stateName: String): Result<Unit>
    fun editState(projectId: String, oldStateName: String, newStateName: String): Result<Unit>
    fun deleteState(projectId: String, stateName: String): Result<Unit>
    fun getAllStatesForProject(projectId: String): List<State>
}