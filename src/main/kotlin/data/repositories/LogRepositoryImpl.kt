package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry

class LogRepositoryImpl(

    private val logDataSource: LogDataSource
) : LogRepository {

    override fun addLog(logEntry: LogEntry) {
        logDataSource.addLog(logEntry)
    }
    override fun getLogByTargetId(targetId: String): List<LogEntry> {
       return logDataSource.getLogByTargetId(targetId)
    }

    override fun getAllLogs(): List<LogEntry> {
      return logDataSource.getAllLogs()
    }

    override fun deleteLogByTargetId(targetId: String) {
        logDataSource.deleteLogByTargetId(targetId)
    }
}