package org.qudus.squad.logic.repositories.log_repository

import org.qudus.squad.model.LogEntry
import java.util.UUID

interface LogRepository {
    fun saveLog(logEntry: LogEntry)
    fun getLogByTargetId(targetId: UUID): List<LogEntry>?
    fun getAllLogs(): List<LogEntry>?
}