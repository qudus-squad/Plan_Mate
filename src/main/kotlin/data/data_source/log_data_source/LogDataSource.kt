package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.model.LogEntry

interface LogDataSource {
    fun getAllLogs(): List<LogEntry>
    fun getProjectByTargetId(targetId: String): LogEntry
    fun deleteLogByTargetId(targetId: String)
    fun addLog(logEntry: LogEntry)
}