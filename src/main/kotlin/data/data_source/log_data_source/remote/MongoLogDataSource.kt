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
        val isLogCreated = logCollection.insertOne(logEntry.toLogEntryDto()).wasAcknowledged()
        if (!isLogCreated)
            throw InvalidToAddLogException()
        return logEntry
    }

    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
        val logs = logCollection.find(eq(TARGET_FIELD, targetId))
            .toList()
            .map { logEntryDto ->
                logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt))
            }
        if (logs.isEmpty()) throw InvalidToGetByIdLogException()
        return logs
    }


    override suspend fun getAllLogs(): List<LogEntry> {

         val logs = logCollection.find().toList().map { logEntryDto ->
                logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt))
        }
        if(logs.isEmpty()) throw InvalidToGetAllLogsException()
        return logs
    }

    override suspend fun deleteLogByTargetId(targetId: String): Boolean {
        val isLogDeleted = logCollection.deleteMany(eq(TARGET_FIELD, targetId)).deletedCount > 0
        if (!isLogDeleted)
            throw InvalidToDeleteLogException()
        return true
    }

    companion object {
        const val TARGET_FIELD = "targetId"
        const val COLLECTION_NAME = "logs"
    }
}