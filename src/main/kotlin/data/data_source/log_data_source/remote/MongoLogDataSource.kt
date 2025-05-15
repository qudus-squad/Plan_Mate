package org.qudus.squad.data.data_source.log_data_source.remote

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.LocalDateTime
import org.qudus.squad.data.data_source.log_data_source.*
import org.qudus.squad.data.data_source.provideCollection
import org.qudus.squad.model.entity.LogEntry

class MongoLogDataSource(
    private val mongoDatabase: MongoDatabase
) : LogDataSource {

    private val logCollection = provideCollection(mongoDatabase, COLLECTION_NAME, LogEntryDto::class.java)
    override suspend fun addNewLog(logEntry: LogEntry): LogEntry {
        return try {
            val result = logCollection.insertOne(logEntry.toLogEntryDto())
            if (result.insertedId != null) {
                logEntry
            } else throw InvalidToAddLogException(FAILED_ADD_LOG)

        } catch (e: Exception) {
            throw InvalidToAddLogException(FAILED_ADD_LOG)
        }
    }

    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
        return try {
            logCollection.find(eq(TARGET_FIELD, targetId)).toList().map { logEntryDto ->
                logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt))
            }
        } catch (e: Exception) {
            throw InvalidToGetByIdLogException(FAILED_GET_LOG_BY_ID)
        }
    }

    override suspend fun getAllLogs(): List<LogEntry> {
        return try {
            logCollection.find().toList().map { logEntryDto ->
                logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt))
            }
        } catch (e: Exception) {
            throw InvalidToGetAllLogsException(FAILED_GET_ALL_LOGS)
        }
    }

    override suspend fun deleteLogByTargetId(targetId: String): Boolean {
        return try {
            logCollection.deleteMany(eq(TARGET_FIELD, targetId))
            true
        } catch (e: Exception) {
            throw InvalidToDeleteLogException(FAILED_DELETE_LOG)
        }
    }

    companion object {
        const val TARGET_FIELD = "targetId"
        const val COLLECTION_NAME = "logs"
    }
}