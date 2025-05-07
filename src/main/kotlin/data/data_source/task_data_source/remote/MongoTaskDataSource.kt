package org.qudus.squad.data.data_source.task_data_source.remote

import com.mongodb.kotlin.client.coroutine.MongoCollection
import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class MongoTaskDataSource(
    private val taskCollection: MongoCollection<TaskDto>
): TaskDataSource {
    override fun createNewTask(task: Task) {
        TODO("Not yet implemented")
    }

    override fun editExistingTask(updatedTask: Task) {
        TODO("Not yet implemented")
    }

    override fun switchTaskState(taskId: String, newTaskState: TaskState) {
        TODO("Not yet implemented")
    }

    override fun deleteTaskById(id: String) {
        TODO("Not yet implemented")
    }

    override fun getAllTasksByProjectId(id: String): List<Task> {
        TODO("Not yet implemented")
    }

    override fun getTaskById(id: String): Task? {
        TODO("Not yet implemented")
    }

    override fun assignTaskToUser(taskId: String, userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun unAssignTask(taskId: String) {
        TODO("Not yet implemented")
    }
}