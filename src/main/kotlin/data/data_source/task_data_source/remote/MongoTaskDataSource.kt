package org.qudus.squad.data.data_source.task_data_source.remote

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import logic.exceptions.TaskCreationException
import logic.exceptions.TaskNotFoundException
import org.bson.conversions.Bson
import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState


class MongoTaskDataSource(
    private val mongoDatabase: MongoDatabase
) : TaskDataSource {
    override suspend fun createNewTask(task: Task): Task {
        val isTaskCreated = getTaskCollection().insertOne(task.toTaskDto()).wasAcknowledged()
        if (!isTaskCreated){
            throw TaskCreationException()
        }
        return getTaskById(task.id)
    }

    override suspend fun editExistingTask(updatedTask: Task) {
        getTaskCollection().replaceOne(eq(TASK_ID, updatedTask.id), updatedTask.toTaskDto())
    }

    override suspend fun switchTaskState(taskId: String, newTaskState: TaskState) {
        val filter = eq(TASK_ID, taskId)
        val updates = Updates.combine(
            Updates.set(TASK_STATE, newTaskState.name),
            Updates.set(TASK_LAST_UPDATED_AT, Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
        )
        getTaskCollection().updateOne(filter, updates)
    }

    override suspend fun deleteTaskById(id: String) {
        val taskById: Bson = eq(TASK_ID, id)
        getTaskCollection().deleteOne(taskById)
    }

    override suspend fun getAllTasksByProjectId(id: String): List<Task> {
        return getTaskCollection().find(eq(TASK_PROJECT_ID, id)).toList().map { it.toTask() }
    }

    override suspend fun getTaskById(id: String): Task {
        val taskById: Bson = eq(TASK_ID, id)
        val taskDto = getTaskCollection().find(taskById).firstOrNull() ?: throw TaskNotFoundException("Task Not Found")
        return taskDto.toTask()
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Boolean {
        val taskById : Bson = eq(TASK_ID,taskId)
        val result = getTaskCollection().updateOne(taskById, Updates.set(TASK_ASSIGNED_USER_ID,userId))
        return result.modifiedCount == 1L
    }

    override suspend fun unAssignTask(taskId: String) {
        val taskById : Bson = eq(TASK_ID,taskId)
        getTaskCollection().updateOne(taskById, Updates.set(TASK_ASSIGNED_USER_ID,""))
    }

    private fun getTaskCollection(): MongoCollection<TaskDto> {
        return mongoDatabase.getCollection<TaskDto>(COLLECTION_NAME)
    }

    companion object {
        const val COLLECTION_NAME = "tasks"
        const val TASK_ID = "id"
        const val TASK_STATE = "taskState"
        const val TASK_PROJECT_ID = "projectId"
        const val TASK_ASSIGNED_USER_ID = "assignedUserId"
        const val TASK_LAST_UPDATED_AT = "lastUpdatedAt"
    }

}