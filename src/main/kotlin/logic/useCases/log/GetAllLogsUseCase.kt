package org.qudus.squad.logic.useCases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry

class GetAllLogsUseCase(private val logRepository: LogRepository) {
    fun getAllLogsUseCas(): List<LogEntry>{
        return logRepository.getAllLogs() ?: emptyList()
    }
}