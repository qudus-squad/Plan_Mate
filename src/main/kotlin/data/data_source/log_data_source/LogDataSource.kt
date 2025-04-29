package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.model.LogEntry

interface LogDataSource {
    fun saveLog(logEntry: LogEntry)
    fun getLogByTargetId(targetId: String): List<LogEntry>?
    fun getAllLogs(): List<LogEntry>?
}