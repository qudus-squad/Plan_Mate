package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.task_state_data_source.TaskStateDataSource
import org.qudus.squad.logic.repositories.TaskStateRepository
import org.qudus.squad.logic.model.TaskState

class TaskStateRepositoryImplementation(
    private val taskStateDataSource: TaskStateDataSource
) : TaskStateRepository {
    override suspend fun gatAllTasksState(): List<TaskState> {
        return taskStateDataSource.getAllTasksState()
    }

    override suspend fun deleteTaskStateById(id: String): Boolean {
        return taskStateDataSource.deleteTaskStateById(id)
    }

    override suspend fun addNewTaskState(taskState: TaskState): TaskState {
        return taskStateDataSource.addNewTaskState(taskState)
    }

    override suspend fun getTaskStateById(id: String): TaskState {
        return getTaskStateById(id)
    }

    override suspend fun editTaskState(taskState: TaskState): Boolean {
        return taskStateDataSource.editTaskState(taskState)
    }
}