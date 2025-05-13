package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry

class GetAllLogsUseCase(
    private val logRepository: LogRepository ,
    private val logEntryValidator: LogEntryDataValidationUseCase
) {
    suspend fun getAllLogs(order: SortOrder = SortOrder.DESCENDING): List<LogEntry>{
        val allLogs: List<LogEntry> = logRepository.getAllLogs()
        allLogs.forEach { logEntryValidator.validateLogEntry(it) }
        return when (order) {
            SortOrder.ASCENDING -> allLogs.sortedBy { it.loggedAt }
            SortOrder.DESCENDING -> allLogs.sortedByDescending { it.loggedAt }
        }
    }
}
enum class SortOrder {
   ASCENDING, DESCENDING
}
