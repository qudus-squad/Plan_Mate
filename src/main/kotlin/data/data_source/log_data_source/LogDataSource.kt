package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.model.LogEntry
import java.util.UUID

interface LogDataSource {
    fun saveLog(logEntry: LogEntry)
    fun getLogByTargetId(targetId: UUID): List<LogEntry>?
    fun getAllLogs(): List<LogEntry>?
}