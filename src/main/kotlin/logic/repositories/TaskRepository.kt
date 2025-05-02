package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

interface TaskRepository {
    fun createNewTask(task: Task)
    fun editExistingTask(updatedTask: Task)
    fun switchTaskState(taskId: String, newTaskState: TaskState)
    fun deleteTaskById(taskId: String)
    fun getAllTasksByProjectId(): List<Task>
    fun getTaskById(id: String): Task
    fun assignTaskToUser(taskId: String, userId: String)
    fun unAssignTask(taskId: String)
}
