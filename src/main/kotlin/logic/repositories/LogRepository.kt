package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.LogEntry

interface LogRepository {
    suspend fun addNewLog(logEntry: LogEntry)
    suspend fun getLogByTargetId(targetId: String): List<LogEntry>
    fun getAllLogs(): List<LogEntry>
    fun deleteLogByTargetId(targetId: String)
}