package org.qudus.squad.logic.repositories

import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.logic.model.TaskState
import org.qudus.squad.logic.model.Task

interface TaskRepository {
    suspend fun createNewTask(task: Task)
    suspend fun editExistingTask(updatedTask: Task)
    suspend fun switchTaskState(taskId: String, newTaskState: TaskState)
    suspend fun deleteTaskById(id: String)
    suspend fun getAllTasksByProjectId(id: String): List<Task>
    suspend fun getTaskById(id:String = GenerateUUID().generate()): Task?
    suspend fun assignTaskToUser(taskId:String, userId: String): Boolean
    suspend fun unAssignTask(taskId: String)
}