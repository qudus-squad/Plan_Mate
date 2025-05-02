package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

class TaskRepositoryImpl(
    private val taskDataSource: TaskDataSource,
) : TaskRepository {
    override fun createNewTask(task: Task) {
        taskDataSource.createNewTask(task)
    }

    override fun editExistingTask(updatedTask: Task) {
        taskDataSource.editExistingTask(updatedTask)
    }

    override fun getAllTasksByProjectId(id: String): List<Task> {
        return taskDataSource.getAllTasksByProjectId(id)
    }

    override fun getTaskById(id: String): Task {
        return taskDataSource.getTaskById(id)
    }

    override fun switchTaskState(taskId: String, newTaskState: TaskState) {
        taskDataSource.switchTaskState(taskId, newTaskState)
    }

    override fun deleteTaskById(id: String) {
        taskDataSource.deleteTaskById(id)
    }

    override fun assignTaskToUser(taskId: String, userId: String) {
        taskDataSource.assignTaskToUser(taskId, userId)
    }

    override fun unAssignTask(taskId: String) {
        taskDataSource.unAssignTask(taskId)
    }
}