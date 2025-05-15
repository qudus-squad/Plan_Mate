package org.qudus.squad.data.data_source.task_state_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.qudus.squad.data.data_source.provideCollection
import org.qudus.squad.data.data_source.task_state_data_source.*
import org.qudus.squad.model.entity.TaskState

class MongoTaskStateDataSource(
    private val mongoDatabase: MongoDatabase
) : TaskStateDataSource {
    private val taskStateCollection = provideCollection(mongoDatabase, COLLECTION_NAME, TaskStateDto::class.java)

    override suspend fun getAllTasksState(): List<TaskState> {
        val tasks = taskStateCollection.find().toList().map { taskStateDto ->
            taskStateDto.toTaskState()
        }
        if (tasks.isEmpty()) throw InvalidToGetAllTaskStatesException()
        return tasks
    }

    override suspend fun deleteTaskStateById(id: String): Boolean {

        val isDeleted = taskStateCollection.deleteOne(Filters.eq(STATE_FIELD, id)).wasAcknowledged()

        if (!isDeleted) throw InvalidToDeleteTaskStateException()
        return true
    }

    override suspend fun addNewTaskState(taskState: TaskState): TaskState {
        val taskStateDto = taskState.toTaskStateDto()
        val isInserted = taskStateCollection.insertOne(taskStateDto).wasAcknowledged()
        if (!isInserted) {
            throw InvalidToAddTaskStateException()
        }
        return taskState
    }

    override suspend fun getTaskStateById(id: String): TaskState {
        val taskStateDto = taskStateCollection.find(Filters.eq(STATE_FIELD, id)).firstOrNull()
            ?: throw InvalidToGetByIdTaskStateException()
        return taskStateDto.toTaskState()
    }

    override suspend fun editTaskState(taskState: TaskState): Boolean {
        val taskStateDto = taskState.toTaskStateDto()
        val isEdited =
            taskStateCollection.replaceOne(Filters.eq(STATE_FIELD, taskState.id), taskStateDto).wasAcknowledged()
        if (!isEdited) throw InvalidToEditTaskStateException()
        return true
    }

    companion object {
        const val STATE_FIELD = "stateId"
        const val COLLECTION_NAME = "tasks_states"
    }
}