package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry

class GetAllLogsUseCase(
    private val logRepository: LogRepository ,
    private val logEntryValidator: LogEntryDataValidationUseCase
) {
    suspend fun getAllLogs(): List<LogEntry>{
        val allLogs: List<LogEntry> = logRepository.getAllLogs()
        allLogs.forEach { logEntryValidator.validateLogEntry(it) }
        return allLogs
    }
}