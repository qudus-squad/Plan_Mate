package org.qudus.squad.logic.repositories

import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

interface TaskRepository {
    fun createNewTask(task: Task)
    fun editExistingTask(updatedTask: Task)
    fun switchTaskState(taskId: String, newTaskState: TaskState)
    fun deleteTaskById(id: String)
    fun getAllTasksByProjectId(id: String): List<Task>
    fun getTaskById(id:String = GenerateUUID().generate()): Task?
    fun assignTaskToUser(taskId:String = GenerateUUID().generate(), userId: String = GenerateUUID().generate()): Boolean
    fun unAssignTask(taskId: String)
}