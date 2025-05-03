package logic.use_cases.log

import org.qudus.squad.logic.exceptions.NoChangeHistoryFoundException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.utils.DateTimeFormatter
import org.qudus.squad.model.entity.LogEntry

class GetLogByTargetIdUseCase(private val logRepository: LogRepository) {

    fun getFormattedLog(targetId: String): List<String> {
        val changeLogs: List<LogEntry> = logRepository.getLogByTargetId(targetId)
            ?: throw NoChangeHistoryFoundException("$NO_CHANGE_HISTORY_FOUND_FOR_ID: $targetId")

        return changeLogs.map { log ->
            "user ${log.userName} changed ${log.targetType.name.lowercase()} ${log.targetId} " +
                    "from ${log.oldValue} to ${log.newValue} at ${DateTimeFormatter.formatDateTimeForDisplay(log.loggedAt)}"
        }
    }
    companion object {
        const val NO_CHANGE_HISTORY_FOUND_FOR_ID = "No change history found for ID"
    }
}