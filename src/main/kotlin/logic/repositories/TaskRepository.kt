package org.qudus.squad.logic.repositories

import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

interface TaskRepository {
    suspend fun createNewTask(task: Task): Task
    suspend fun editExistingTask(updatedTask: Task): Boolean
    suspend fun switchTaskState(taskId: String, newTaskState: TaskState): Boolean
    suspend fun deleteTaskById(id: String): Boolean
    suspend fun getAllTasksByProjectId(id: String): List<Task>
    suspend fun getTaskById(id: String): Task
    suspend fun assignTaskToUser(taskId: String, userId: String): Boolean
    suspend fun unAssignTask(taskId: String): Boolean
    suspend fun getAllTasks(): List<Task>
}