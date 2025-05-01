package fakes

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.State

class FakeStateRepository : StateRepository {

    private val statesPerProject = mutableMapOf<String, MutableList<State>>()

    override fun addStateToProject(projectId: String, state: State): Result<Unit> {
        val states = statesPerProject.getOrPut(projectId) { mutableListOf() }
        states.add(state)
        return Result.success(Unit)
    }

    override fun editState(projectId: String, oldState: State, modifiedState: State): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))

        val index = states.indexOfFirst { it.id == oldState.id }
        if (index == -1) return Result.failure(Exception("State not found"))

        val updatedState = State(id = oldState.id, name = modifiedState.name)
        states[index] = updatedState

        return Result.success(Unit)
    }

    override fun deleteState(projectId: String, state: State): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))

        val removed = states.removeIf { it.id == state.id }
        return if (removed) Result.success(Unit) else Result.failure(Exception("State not found"))
    }

    override fun getAllStatesForProject(projectId: String): List<State> {
        return statesPerProject[projectId]?.toList() ?: emptyList()
    }
}