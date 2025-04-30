package org.qudus.squad.logic.repositories

import org.qudus.squad.model.State
import org.qudus.squad.model.Task

interface TaskRepository {
    fun createTask(task: Task): Result<Unit>
    fun editTask(updatedTask: Task): Result<Unit>
    fun deleteTask(taskId: String): Result<Unit>
    fun getAllTasks(): List<Task>
    fun switchTaskState(taskId: String, newState: State): Result<Unit>
    fun assignTaskTo(taskId: String, userId: String): Result<Unit>
    fun unAssignTask(taskId: String): Result<Unit>
}