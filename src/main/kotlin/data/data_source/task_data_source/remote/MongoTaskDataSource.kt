package org.qudus.squad.data.data_source.task_data_source.remote

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.bson.conversions.Bson
import org.qudus.squad.data.data_source.provideCollection
import org.qudus.squad.data.data_source.task_data_source.*
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState


class MongoTaskDataSource(
    private val mongoDatabase: MongoDatabase
) : TaskDataSource {
    private val taskCollection = provideCollection(mongoDatabase, COLLECTION_NAME, TaskDto::class.java)

    override suspend fun createNewTask(task: Task): Task {
        return try {
            taskCollection.insertOne(task.toTaskDto())
            task
        } catch (e: Exception) {
            throw InvalidToAddTaskException(FAILED_ADD_TASK)
        }
    }

    override suspend fun editExistingTask(updatedTask: Task): Boolean {
        return try {
            taskCollection.replaceOne(eq(TASK_ID, updatedTask.id), updatedTask.toTaskDto())
            true
        } catch (e: Exception) {
            throw InvalidToEditTaskException(FAILED_EDIT_TASK)
        }
    }

    override suspend fun switchTaskState(taskId: String, newTaskState: TaskState): Boolean {
        return try {
            val filter = eq(TASK_ID, taskId)
            val updates = Updates.combine(
                Updates.set(TASK_STATE, newTaskState.name),
                Updates.set(TASK_LAST_UPDATED_AT, Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
            )
            taskCollection.updateOne(filter, updates)
            true
        } catch (e: Exception) {
            throw InvalidToEditTaskException(FAILED_EDIT_TASK)
        }
    }

    override suspend fun deleteTaskById(id: String): Boolean {
        return try {
            val taskById: Bson = eq(TASK_ID, id)
            taskCollection.deleteOne(taskById)
            true
        } catch (e: Exception) {
            throw InvalidToDeleteTaskException(FAILED_DELETE_TASK)
        }
    }

    override suspend fun getAllTasksByProjectId(id: String): List<Task> {
        return try {
            taskCollection.find(eq(TASK_PROJECT_ID, id)).toList().map {
                it.toTask()
            }
        } catch (e: Exception) {
            throw InvalidToGetAllTasksException(FAILED_GET_ALL_TASKS)
        }
    }

    override suspend fun getTaskById(id: String): Task {
        return try {
            val taskById: Bson = eq(TASK_ID, id)
            val taskDto = taskCollection.find(taskById).first()
            taskDto.toTask()
        } catch (e: Exception) {
            throw InvalidToGetTaskByIdTaskException(FAILED_GET_TASK_BY_ID)
        }
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Boolean {
        return try {
            val taskById: Bson = eq(TASK_ID, taskId)
            val result = taskCollection.updateOne(taskById, Updates.set(TASK_ASSIGNED_USER_ID, userId))
            result.modifiedCount == 1L
        } catch (e: Exception) {
            throw InvalidToEditTaskException(FAILED_EDIT_TASK)
        }
    }

    override suspend fun unAssignTask(taskId: String): Boolean {
        return try {
            val taskById: Bson = eq(TASK_ID, taskId)
            val result = taskCollection.updateOne(taskById, Updates.set(TASK_ASSIGNED_USER_ID, ""))
            if (result.modifiedCount == 1L) true
            else throw InvalidToEditTaskException(FAILED_EDIT_TASK)
        } catch (e: Exception) {
            throw InvalidToEditTaskException(FAILED_EDIT_TASK)
        }
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