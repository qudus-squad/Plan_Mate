package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.LogEntry

interface LogRepository {
    fun addLog(logEntry: LogEntry)
    fun getLogByTargetId(targetId: String): List<LogEntry>?
    fun getAllLogs(): List<LogEntry>?
    fun deleteLogByTargetId(targetId: String)
}