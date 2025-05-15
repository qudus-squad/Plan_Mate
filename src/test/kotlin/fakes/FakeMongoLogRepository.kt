package fakes

import kotlinx.datetime.LocalDateTime
import org.qudus.squad.data.data_source.log_data_source.remote.LogEntryDto
import org.qudus.squad.data.data_source.log_data_source.remote.toLogEntry
import org.qudus.squad.data.data_source.log_data_source.remote.toLogEntryDto
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry

class FakeMongoLogRepository : LogRepository {

    private val list = mutableListOf<LogEntryDto>()

    override suspend fun addNewLog(logEntry: LogEntry): LogEntry {
        list.add(logEntry.toLogEntryDto())
        return logEntry
    }

    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
        return list.filter { it.targetId == targetId }.map { logEntryDto ->
            logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt))
        }
    }

    override suspend fun getAllLogs(): List<LogEntry> {
        return list.map { logEntryDto ->
            logEntryDto.toLogEntry().copy(loggedAt = LocalDateTime.parse(logEntryDto.loggedAt))
        }
    }

    override suspend fun deleteLogByTargetId(targetId: String): Boolean {
        return list.removeIf { it.targetId == targetId }
    }
}
