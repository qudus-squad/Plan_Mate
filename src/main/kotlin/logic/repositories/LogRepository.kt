package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.LogEntry

interface LogRepository {
    suspend fun addNewLog(logEntry: LogEntry)
    suspend fun getLogByTargetId(targetId: String): List<LogEntry>
    suspend fun getAllLogs(): List<LogEntry>
    suspend fun deleteLogByTargetId(targetId: String)
}