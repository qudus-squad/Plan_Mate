package org.qudus.squad.data.repositories.log_repository

import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.logic.repositories.log_repository.LogRepository
import org.qudus.squad.model.LogEntry
import java.util.UUID

class LogRepositoryImpl(
   private val logDataSource: LogDataSource
): LogRepository {
    override fun saveLog(logEntry: LogEntry) {
        TODO("Not yet implemented")
    }

    override fun getLogByTargetId(targetId: UUID): List<LogEntry> {
        TODO("Not yet implemented")
    }

    override fun getAllLogs(): List<LogEntry> {
        TODO("Not yet implemented")
    }
}