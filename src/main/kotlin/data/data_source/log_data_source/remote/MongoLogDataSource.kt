package org.qudus.squad.data.data_source.log_data_source.remote

import com.mongodb.kotlin.client.coroutine.MongoCollection
import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.model.entity.LogEntry

class MongoLogDataSource(
    private val logCollection: MongoCollection<LogEntryDto>
): LogDataSource {
    override suspend fun addNewLog(logEntry: LogEntry) {
        logCollection.insertOne(logEntry.toLogEntryDto())
    }

    override fun getLogByTargetId(targetId: String): List<LogEntry> {
        TODO("Not yet implemented")
    }

    override fun getAllLogs(): List<LogEntry> {
        TODO("Not yet implemented")
    }

    override fun deleteLogByTargetId(targetId: String) {
        TODO("Not yet implemented")
    }
}