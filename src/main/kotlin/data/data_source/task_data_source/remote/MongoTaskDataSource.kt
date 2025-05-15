package org.qudus.squad.data.data_source.task_data_source.remote

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.data_source.provideCollection
import org.qudus.squad.data.data_source.task_data_source.*
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState


class MongoTaskDataSource(
    private val mongoDatabase: MongoDatabase
) : TaskDataSource {
    private val taskCollection = provideCollection(mongoDatabase, COLLECTION_NAME, TaskDto::class.java)

    override suspend fun createNewTask(task: Task): Task {
        val isCreated = taskCollection.insertOne(task.toTaskDto()).wasAcknowledged()
        if (!isCreated) throw InvalidToAddTaskException()
        return task
    }

    override suspend fun editExistingTask(updatedTask: Task): Boolean {
        val isEdited = taskCollection.replaceOne(eq(TASK_ID, updatedTask.id), updatedTask.toTaskDto()).wasAcknowledged()
        if (!isEdited) throw InvalidToEditTaskException()
        return true
    }

    override suspend fun switchTaskState(taskId: String, newTaskState: TaskState): Boolean {
        val filter = eq(TASK_ID, taskId)
        val updates = Updates.combine(
            Updates.set(TASK_STATE, newTaskState.name), Updates.set(
                TASK_LAST_UPDATED_AT, Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
        val isUpdated = taskCollection.updateOne(filter, updates).wasAcknowledged()
        if (!isUpdated) throw InvalidToEditTaskException()
        return true
    }

    override suspend fun deleteTaskById(id: String): Boolean {
        val isDeleted = taskCollection.deleteOne(eq(TASK_ID, id))
        if (isDeleted.deletedCount == 0L) throw InvalidToDeleteTaskException()
        return true
    }

    override suspend fun getAllTasksByProjectId(id: String): List<Task> {
        val tasks = taskCollection.find(eq(TASK_PROJECT_ID, id)).toList().map { taskDto -> taskDto.toTask() }
        if (tasks.isEmpty()) {
            throw InvalidToGetAllTasksException()
        }
        return tasks
    }

    override suspend fun getTaskById(id: String): Task {
        val taskDto = taskCollection.find(eq(TASK_ID, id)).firstOrNull() ?: throw InvalidToGetTaskByIdTaskException()
        return taskDto.toTask()
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Boolean {
        val isAssigned =
            taskCollection.updateOne(eq(TASK_ID, taskId), Updates.set(TASK_ASSIGNED_USER_ID, userId)).wasAcknowledged()
        if (!isAssigned) throw InvalidToEditTaskException()
        return true
    }

    override suspend fun unAssignTask(taskId: String): Boolean {
        val isUnassigned =
            taskCollection.updateOne(eq(TASK_ID, taskId), Updates.set(TASK_ASSIGNED_USER_ID, "")).wasAcknowledged()
        if (!isUnassigned) throw InvalidToEditTaskException()
        return true
    }

    override suspend fun getAllTasks(): List<Task> {
        return taskCollection.find().toList().map { it.toTask() }
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