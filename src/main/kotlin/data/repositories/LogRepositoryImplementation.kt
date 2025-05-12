package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.model.LogEntry

class LogRepositoryImplementation(
    private val logDataSource: LogDataSource
) : LogRepository {

    override suspend fun addNewLog(logEntry: LogEntry) {
        logDataSource.addNewLog(logEntry)
    }
    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
       return logDataSource.getLogByTargetId(targetId)
    }

    override suspend fun getAllLogs(): List<LogEntry> {
      return logDataSource.getAllLogs()
    }

    override suspend fun deleteLogByTargetId(targetId: String) {
        logDataSource.deleteLogByTargetId(targetId)
    }
}