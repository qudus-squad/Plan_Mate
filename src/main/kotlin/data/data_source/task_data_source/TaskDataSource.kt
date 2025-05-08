package org.qudus.squad.data.data_source.task_data_source

import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

interface TaskDataSource {
    suspend fun createNewTask(task: Task)
    suspend fun editExistingTask(updatedTask: Task)
    suspend fun switchTaskState(taskId: String, newTaskState: TaskState)
    suspend fun deleteTaskById(id: String)
    suspend fun getAllTasksByProjectId(id: String): List<Task>
    suspend fun getTaskById(id: String): Task?
    suspend fun assignTaskToUser(taskId: String, userId: String): Boolean
    suspend fun unAssignTask(taskId: String)
}