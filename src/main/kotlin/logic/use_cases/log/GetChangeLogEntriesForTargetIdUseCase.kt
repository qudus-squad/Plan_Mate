package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.ui.utils.DateTimeFormatter

class GetChangeLogEntriesForTargetIdUseCase(
    private val logRepository: LogRepository,
    private val logEntryValidator: LogEntryDataValidationUseCase
) {
    suspend fun getFormattedLog(targetId: String): List<FormattedLogEntry> {
        val changeLogs: List<LogEntry> = logRepository.getLogByTargetId(targetId)
        changeLogs.forEach { logEntryValidator.validateLogEntry(it) }

        return changeLogs.map { log ->
            FormattedLogEntry(
                userName = log.userName,
                target = "${log.targetType.name.lowercase()} ${log.targetId}",
                changeValue = "${log.oldValue} to ${log.newValue}",
                time = DateTimeFormatter.formatDateTimeForDisplay(log.loggedAt)
            )
        }
    }
}
data class FormattedLogEntry(
    val userName: String,
    val target: String,
    val changeValue: String,
    val time: String
)