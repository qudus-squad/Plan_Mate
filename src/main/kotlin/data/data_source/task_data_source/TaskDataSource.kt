package org.qudus.squad.data.data_source.task_data_source

import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

interface TaskDataSource {
    fun createNewTask(task: Task)
    fun editExistingTask(updatedTask: Task)
    fun switchTaskState(taskId: String, newTaskState: TaskState)
    fun deleteTaskById(id: String)
    fun getAllTasksByProjectId(id: String): List<Task>
    fun getTaskById(id: String): Task?
    fun assignTaskToUser(taskId: String, userId: String): Boolean
    fun unAssignTask(taskId: String)
}