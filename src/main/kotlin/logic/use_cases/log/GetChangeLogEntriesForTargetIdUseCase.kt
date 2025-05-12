package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.ui.utils.DateTimeFormatter

class GetChangeLogEntriesForTargetIdUseCase(
    private val logRepository: LogRepository, private val logEntryValidator: LogEntryDataValidationUseCase
) {
    suspend fun getFormattedLog(targetId: String): List<String> {
        val changeLogs: List<LogEntry> = logRepository.getLogByTargetId(targetId)
        changeLogs.forEach { logEntryValidator.validateLogEntry(it) }

        return changeLogs.map { log ->
            "user ${log.userName} changed ${log.targetType.name.lowercase()} ${log.targetId} " + "from ${log.oldValue} to ${log.newValue} at ${
                DateTimeFormatter.formatDateTimeForDisplay(
                    log.loggedAt
                )
            }"
        }
    }
}