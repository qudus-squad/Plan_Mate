package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.model.entity.LogEntry

interface LogDataSource {
    suspend fun addNewLog(logEntry: LogEntry): LogEntry
    suspend fun getLogByTargetId(targetId: String): List<LogEntry>
    suspend fun getAllLogs(): List<LogEntry>
    suspend fun deleteLogByTargetId(targetId: String): Boolean
}