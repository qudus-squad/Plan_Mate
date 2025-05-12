package org.qudus.squad.data.data_source.task_state_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.exceptions.FailedCreatingTaskStateException
import logic.exceptions.TaskStateNotFoundException
import org.qudus.squad.data.data_source.task_state_data_source.TaskStateDataSource
import org.qudus.squad.logic.model.TaskState

class MongoTaskStateDataSource(
    private val mongoDatabase: MongoDatabase
) : TaskStateDataSource {
    override suspend fun getAllTasksState(): List<TaskState> {
        return provideTaskStateCollection(mongoDatabase).find().toList().map { taskStateDto ->
            taskStateDto.toTaskState()
        }
    }

    override suspend fun deleteTaskStateById(id: String): Boolean {
        return try {
            val result = provideTaskStateCollection(mongoDatabase).deleteOne(Filters.eq("stateId", id))
            result.deletedCount > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun addNewTaskState(taskState: TaskState): TaskState {
        val tasStateDto = taskState.toTaskStateDto()
        val result = provideTaskStateCollection(mongoDatabase).insertOne(tasStateDto)
        return result.insertedId?.let {
            taskState
        } ?: throw FailedCreatingTaskStateException()
    }

    override suspend fun getTaskStateById(id: String): TaskState {
        val taskStateDto = provideTaskStateCollection(mongoDatabase).find(Filters.eq("stateId", id)).firstOrNull()
            ?: throw TaskStateNotFoundException()
        return taskStateDto.toTaskState()
    }

    override suspend fun editTaskState(taskState: TaskState): Boolean {
        val taskStateDto = taskState.toTaskStateDto()
        val result =
            provideTaskStateCollection(mongoDatabase).replaceOne(Filters.eq("stateId", taskState.id), taskStateDto)
        return result.modifiedCount > 0
    }

    private fun provideTaskStateCollection(database: MongoDatabase): MongoCollection<TaskStateDto> {
        return database.getCollection<TaskStateDto>("tasks_states")
            .withDocumentClass(TaskStateDto::class.java)
    }
}