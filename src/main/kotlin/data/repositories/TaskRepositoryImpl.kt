package org.qudus.squad.data.repositories

import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

class TaskRepositoryImpl: TaskRepository {
    override fun createNewTask(task: Task){
        TODO("Not yet implemented")
    }

    override fun editExistingTask(updatedTask: Task){
        TODO("Not yet implemented")
    }

    override fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun getAllTasksByProjectId(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun switchTaskState(taskId: String, newTaskState: TaskState) {
        TODO("Not yet implemented")
    }

    override fun assignTaskToUser(taskId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override fun unAssignTask(taskId: String) {
        TODO("Not yet implemented")
    }
}