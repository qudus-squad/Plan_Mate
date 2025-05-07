package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

class TaskRepositoryImplementation(
    private val taskDataSource: TaskDataSource,
) : TaskRepository {
    override suspend fun createNewTask(task: Task) {
        taskDataSource.createNewTask(task)
    }

    override suspend fun editExistingTask(updatedTask: Task) {
        taskDataSource.editExistingTask(updatedTask)
    }

    override suspend fun getAllTasksByProjectId(id: String): List<Task> {
        return taskDataSource.getAllTasksByProjectId(id)
    }

    override suspend fun getTaskById(id: String): Task? {
        return taskDataSource.getTaskById(id)
    }

    override suspend fun switchTaskState(taskId: String, newTaskState: TaskState) {
        taskDataSource.switchTaskState(taskId, newTaskState)
    }

    override suspend fun deleteTaskById(id: String) {
        taskDataSource.deleteTaskById(id)
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Boolean {
        return taskDataSource.assignTaskToUser(taskId, userId)
    }

    override fun unAssignTask(taskId: String) {
        taskDataSource.unAssignTask(taskId)
    }
}