package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.entity.TaskState

class StateRepositoryImpl (private val dataSource: TaskDataSource) : StateRepository {
    override fun addTaskStateToProject(projectId: String, taskState: TaskState): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun editTaskState(projectId: String, oldTaskState: TaskState, modifiedTaskState: TaskState): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteTaskState(projectId: String, taskState: TaskState): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getAllTaskStatesByProjectId(projectId: String): List<TaskState> {
        TODO("Not yet implemented")
    }
}