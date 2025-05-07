package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.utils.DateTimeFormatter
import org.qudus.squad.model.entity.LogEntry
class GetChangeLogEntriesForTargetIdUseCase(private val logRepository: LogRepository) {

    suspend fun getFormattedLog(targetId: String): List<String> {
        val changeLogs: List<LogEntry> = logRepository.getLogByTargetId(targetId)

        return changeLogs.map { log ->
            "user ${log.userName} changed ${log.targetType.name.lowercase()} ${log.targetId} " +
                    "from ${log.oldValue} to ${log.newValue} at ${DateTimeFormatter.formatDateTimeForDisplay(log.loggedAt)}"
        }
    }
}