package org.qudus.squad.logic.repositories.log_repository

import org.qudus.squad.model.LogEntry

interface LogRepository {
    fun saveLog(logEntry: LogEntry)
    fun getLogByTargetId(targetId: String): List<LogEntry>?
    fun getAllLogs(): List<LogEntry>?
}