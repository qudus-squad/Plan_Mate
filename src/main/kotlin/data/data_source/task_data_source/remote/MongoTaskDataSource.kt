package org.qudus.squad.data.data_source.task_data_source.remote

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.conversions.Bson
import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull


class MongoTaskDataSource(
    private val mongoDatabase: MongoDatabase
): TaskDataSource {
    override suspend fun createNewTask(task: Task) {
       getTaskCollection().insertOne(task.toTaskDto())
    }

    override fun editExistingTask(updatedTask: Task) {
        TODO("Not yet implemented")
    }

    override fun switchTaskState(taskId: String, newTaskState: TaskState) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskById(id: String) {
        val filteredId : Bson = eq("id",id)
        getTaskCollection().deleteOne(filteredId)
    }

    override fun getAllTasksByProjectId(id: String): List<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(id: String): Task? {
        val filteredId : Bson = eq("id",id)
        val taskDto = getTaskCollection().find(filteredId).firstOrNull()
        return taskDto?.toTask()
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun unAssignTask(taskId: String) {
        TODO("Not yet implemented")
    }

    private fun getTaskCollection(): MongoCollection<TaskDto>{
        return mongoDatabase.getCollection<TaskDto>("tasks")
    }

}