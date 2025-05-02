package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.model.entity.LogEntry

interface LogDataSource {
    fun addLog(logEntry: LogEntry)
    fun getProjectByTargetId(targetId: String): LogEntry
    fun getAllLogs(): List<LogEntry>
    fun deleteLogByTargetId(targetId: String)
}