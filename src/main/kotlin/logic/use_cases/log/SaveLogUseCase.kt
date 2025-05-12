package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry

class SaveLogUseCase(
    private val logRepository: LogRepository,
    private val logEntryValidator: LogEntryDataValidationUseCase
) {
    suspend fun saveLog(logEntry: LogEntry) {
        logEntryValidator.validateLogEntry(logEntry)
        logRepository.addNewLog(logEntry)
    }
}