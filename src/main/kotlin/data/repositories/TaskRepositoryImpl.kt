package org.qudus.squad.data.repositories

import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.State
import org.qudus.squad.model.Task

class TaskRepositoryImpl: TaskRepository {
    override fun createTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun editTask(updatedTask: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteTask(taskId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun switchTaskState(taskId: String, newState: State): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun assignTaskTo(taskId: String, userId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun unAssignTask(taskId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}