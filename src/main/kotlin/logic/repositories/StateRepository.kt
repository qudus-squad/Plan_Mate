package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.TaskState

interface StateRepository {
    fun addTaskStateToProject(projectId: String, taskState: TaskState): Result<Unit>
    fun editTaskState(projectId: String, oldTaskState: TaskState, modifiedTaskState: TaskState): Result<Unit>
    fun deleteTaskState(projectId: String, taskState: TaskState): Result<Unit>
    fun getAllTaskStatesByProjectId(projectId: String): List<TaskState>
}