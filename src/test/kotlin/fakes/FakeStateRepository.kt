package fakes

import org.qudus.squad.logic.repositories.state_repository.StateRepository
import org.qudus.squad.model.State

class FakeStateRepository : StateRepository {

    private val statesPerProject = mutableMapOf<String, MutableList<State>>()

    override fun addStateToProject(projectId: String, stateName: String): Result<Unit> {
        val states = statesPerProject.getOrPut(projectId) { mutableListOf() }
        if (states.any { it.name == stateName }) {
            return Result.failure(Exception("State already exists"))
        }
        states.add(State(name = stateName))
        return Result.success(Unit)
    }

    override fun editState(projectId: String, oldStateName: String, newStateName: String): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))
        val index = states.indexOfFirst { it.name == oldStateName }
        if (index == -1) return Result.failure(Exception("Old state not found"))

        states[index] = State(name = newStateName)
        return Result.success(Unit)
    }

    override fun deleteState(projectId: String, stateName: String): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))

        val removed = states.removeIf { it.name == stateName }
        return if (removed) Result.success(Unit) else Result.failure(Exception("State not found"))
    }

    override fun getAllStatesForProject(projectId: String): List<State> {
        return statesPerProject[projectId]?.toList() ?: emptyList()
    }
}