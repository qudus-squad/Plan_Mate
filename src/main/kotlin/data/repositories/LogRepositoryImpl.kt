package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry

class LogRepositoryImpl(
    private val logDataSource: LogDataSource
) : LogRepository {
    override fun addLog(logEntry: LogEntry) {
        TODO("Not yet implemented")
    }

    override fun getLogByTargetId(targetId: String): List<LogEntry> {
        TODO("Not yet implemented")
    }

    override fun getAllLogs(): List<LogEntry> {
        TODO("Not yet implemented")
    }

    override fun deleteLogByTargetId(targetId: String) {
        TODO("Not yet implemented")
    }
}