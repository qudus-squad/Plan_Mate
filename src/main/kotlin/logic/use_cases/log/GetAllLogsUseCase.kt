package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry

class GetAllLogsUseCase(private val logRepository: LogRepository) {
    suspend fun getAllLogs(): List<LogEntry>{
        return logRepository.getAllLogs()
    }
}