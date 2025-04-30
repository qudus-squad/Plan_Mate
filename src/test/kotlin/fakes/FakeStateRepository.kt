package fakes

import org.qudus.squad.logic.repositories.state_repository.StateRepository
import org.qudus.squad.model.State

class FakeStateRepository : StateRepository {

    private val statesPerProject = mutableMapOf<String, MutableList<State>>()

    override fun addStateToProject(projectId: String, state: String): Result<Unit> {
        val states = statesPerProject.getOrPut(projectId) { mutableListOf() }
        if (states.any { it.name == state }) {
            return Result.failure(Exception("State already exists"))
        }
        states.add(State(name = state))
        return Result.success(Unit)
    }

    override fun editState(projectId: String, state: String, newStateName: String): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))
        val index = states.indexOfFirst { it.name == state }
        if (index == -1) return Result.failure(Exception("Old state not found"))

        states[index] = State(name = newStateName)
        return Result.success(Unit)
    }

    override fun deleteState(projectId: String, state: String): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))

        val removed = states.removeIf { it.name == state }
        return if (removed) Result.success(Unit) else Result.failure(Exception("State not found"))
    }

    override fun getAllStatesForProject(projectId: String): List<State> {
        return statesPerProject[projectId]?.toList() ?: emptyList()
    }
}