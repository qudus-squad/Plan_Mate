package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.model.entity.LogEntry

interface LogDataSource {
    suspend fun addNewLog(logEntry: LogEntry)
    fun getLogByTargetId(targetId: String): List<LogEntry>
    fun getAllLogs(): List<LogEntry>
    fun deleteLogByTargetId(targetId: String)
}