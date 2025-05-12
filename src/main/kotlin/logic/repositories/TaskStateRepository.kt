package org.qudus.squad.logic.repositories

import org.qudus.squad.logic.model.TaskState

interface TaskStateRepository {
    suspend fun gatAllTasksState(): List<TaskState>
    suspend fun deleteTaskStateById(id: String): Boolean
    suspend fun addNewTaskState(taskState: TaskState): TaskState
    suspend fun getTaskStateById(id: String): TaskState
    suspend fun editTaskState(taskState: TaskState): Boolean
}