package org.qudus.squad.data.data_source.task_data_source.remote

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.conversions.Bson
import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState


class MongoTaskDataSource(
    private val mongoDatabase: MongoDatabase
) : TaskDataSource {
    override suspend fun createNewTask(task: Task) {
        getTaskCollection().insertOne(task.toTaskDto())
    }

    override suspend fun editExistingTask(updatedTask: Task) {
        getTaskCollection().replaceOne(eq(TaskDto.TASK_ID, updatedTask.id), updatedTask.toTaskDto())
    }

    override suspend fun switchTaskState(taskId: String, newTaskState: TaskState) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskById(id: String) {
        val filteredId: Bson = eq(TaskDto.TASK_ID, id)
        getTaskCollection().deleteOne(filteredId)
    }

    override suspend fun getAllTasksByProjectId(id: String): List<Task> {
        return getTaskCollection().find(eq(TaskDto.TASK_PROJECT_ID, id)).toList().map { it.toTask() }
    }

    override suspend fun getTaskById(id: String): Task? {
        val filteredId: Bson = eq(TaskDto.TASK_ID, id)
        val taskDto = getTaskCollection().find(filteredId).firstOrNull()
        return taskDto?.toTask()
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Boolean {
        val taskById : Bson = eq(TaskDto.TASK_ID,taskId)
        val result = getTaskCollection().updateOne(taskById, Updates.set(TaskDto.TASK_ASSIGNED_USER_ID,userId))
        return result.modifiedCount == 1L
    }

    override fun unAssignTask(taskId: String) {
        TODO("Not yet implemented")
    }

    private fun getTaskCollection(): MongoCollection<TaskDto> {
        return mongoDatabase.getCollection<TaskDto>("tasks")
    }

}