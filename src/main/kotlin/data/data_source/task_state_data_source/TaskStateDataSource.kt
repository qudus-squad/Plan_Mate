package org.qudus.squad.data.data_source.task_state_data_source

import org.qudus.squad.model.entity.TaskState

interface TaskStateDataSource {
    suspend fun getAllTasksState(): List<TaskState>
    suspend fun deleteTaskStateById(id: String): Boolean
    suspend fun addNewTaskState(taskState: TaskState): TaskState
    suspend fun getTaskStateById(id: String): TaskState
    suspend fun editTaskState(taskState: TaskState): Boolean
}