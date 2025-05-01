package fakes

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.entity.TaskState

class FakeStateRepository : StateRepository {

    private val statesPerProject = mutableMapOf<String, MutableList<TaskState>>()

    override fun addTaskStateToProject(projectId: String, taskState: TaskState): Result<Unit> {
        val states = statesPerProject.getOrPut(projectId) { mutableListOf() }
        states.add(taskState)
        return Result.success(Unit)
    }

    override fun editTaskState(projectId: String, oldTaskState: TaskState, modifiedTaskState: TaskState): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))

        val index = states.indexOfFirst { it.id == oldTaskState.id }
        if (index == -1) return Result.failure(Exception("State not found"))

        val updatedTaskState = TaskState(id = oldTaskState.id, name = modifiedTaskState.name)
        states[index] = updatedTaskState

        return Result.success(Unit)
    }

    override fun deleteTaskState(projectId: String, taskState: TaskState): Result<Unit> {
        val states = statesPerProject[projectId] ?: return Result.failure(Exception("Project not found"))

        val removed = states.removeIf { it.id == taskState.id }
        return if (removed) Result.success(Unit) else Result.failure(Exception("State not found"))
    }

    override fun getAllTaskStatesByProjectId(projectId: String): List<TaskState> {
        return statesPerProject[projectId]?.toList() ?: emptyList()
    }
}