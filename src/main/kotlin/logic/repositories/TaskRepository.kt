package org.qudus.squad.logic.repositories

import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

interface TaskRepository {
    suspend fun createNewTask(task: Task)
    suspend fun editExistingTask(updatedTask: Task)
    suspend fun switchTaskState(taskId: String, newTaskState: TaskState)
    suspend fun deleteTaskById(id: String)
    suspend fun getAllTasksByProjectId(id: String): List<Task>
    suspend fun getTaskById(id:String = GenerateUUID().generate()): Task?
    suspend fun assignTaskToUser(taskId:String = GenerateUUID().generate(), userId: String = GenerateUUID().generate()): Boolean
    fun unAssignTask(taskId: String)
}