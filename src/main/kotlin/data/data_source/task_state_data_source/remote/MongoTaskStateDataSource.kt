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

        return try {
            taskStateCollection.find().toList().map { taskStateDto ->
                taskStateDto.toTaskState()
            }
        } catch (e: Exception) {
            throw InvalidToGetAllTaskStatesException(
                FAILED_GET_ALL_TASK_STATES
            )
        }
    }

    override suspend fun deleteTaskStateById(id: String): Boolean {
        return try {
            val result = taskStateCollection.deleteOne(Filters.eq(STATE_FIELD, id))
            result.deletedCount > 0
        } catch (e: Exception) {
            throw InvalidToDeleteTaskStateException(
                FAILED_DELETE_TASK_STATE
            )
        }
    }

    override suspend fun addNewTaskState(taskState: TaskState): TaskState {
        return try {
            val taskStateDto = taskState.toTaskStateDto()
            val result = taskStateCollection.insertOne(taskStateDto)
            result.insertedId?.let {
                taskState
            } ?: throw InvalidToAddTaskStateException(FAILED_ADD_TASK_STATE)
        } catch (e: Exception) {
            throw InvalidToAddTaskStateException(FAILED_ADD_TASK_STATE)
        }
    }

    override suspend fun getTaskStateById(id: String): TaskState {
        val taskStateDto = taskStateCollection.find(Filters.eq(STATE_FIELD, id)).firstOrNull()
            ?: throw InvalidToGetByIdTaskStateException(FAILED_GET_TASK_STATE_BY_ID)
        return taskStateDto.toTaskState()
    }

    override suspend fun editTaskState(taskState: TaskState): Boolean {
        return try {
            val taskStateDto = taskState.toTaskStateDto()
            val result = taskStateCollection.replaceOne(Filters.eq(STATE_FIELD, taskState.id), taskStateDto)
            result.modifiedCount > 0
        } catch (e: Exception) {
            throw InvalidToEditTaskStateException(FAILED_DELETE_TASK_STATE)
        }
    }

    companion object {
        const val STATE_FIELD = "stateId"
        const val COLLECTION_NAME = "tasks_states"
    }
}