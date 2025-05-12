package org.qudus.squad.data.data_source.log_data_source.remote

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import kotlinx.datetime.LocalDateTime
import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.model.entity.LogEntry

class MongoLogDataSource(
    private val mongoDatabase: MongoDatabase
) : LogDataSource {


    override suspend fun addNewLog(logEntry: LogEntry) {
        provideLogCollection(mongoDatabase).insertOne(logEntry.toLogEntryDto())
    }

    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
        return provideLogCollection(mongoDatabase).find(eq(TARGET_FILED, targetId)).toList()
            .map { logEntryDto -> logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt)) }
    }

    override suspend fun getAllLogs(): List<LogEntry> {
        return provideLogCollection(mongoDatabase).find().toList().map { logEntryDto ->
            logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt))
        }
    }

    override suspend fun deleteLogByTargetId(targetId: String) {
        provideLogCollection(mongoDatabase).deleteMany(eq(TARGET_FILED, targetId))
    }

    private fun provideLogCollection(database: MongoDatabase): MongoCollection<LogEntryDto> {
        return database.getCollection<LogEntryDto>(COLLECTION_NAME).withDocumentClass(LogEntryDto::class.java)
    }

    companion object {
        const val TARGET_FILED = "targetId"
        const val COLLECTION_NAME = "logs"
    }

}