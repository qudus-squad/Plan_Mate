package org.qudus.squad.data.data_source.log_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.model.entity.LogEntry

class MongoLogDataSource(
    private val mongoDataBase: MongoDatabase
): LogDataSource {
    override suspend fun addNewLog(logEntry: LogEntry) {
        mongoDataBase.getCollection<LogEntryDto>("logs").insertOne(logEntry.toLogEntryDto())
    }

    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
        val dtoLogs= mongoDataBase.getCollection<LogEntryDto>("logs").find(Filters.eq("targetId"))
        return  dtoLogs.toList()
            .map { it.toLogEntry() }
    }
    override fun getAllLogs(): List<LogEntry> {
        TODO("Not yet implemented")
    }

    override fun deleteLogByTargetId(targetId: String) {
        TODO("Not yet implemented")
    }
}