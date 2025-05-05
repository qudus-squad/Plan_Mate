package logic.use_cases.log

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry

class SaveLogUseCase(private val logRepository: LogRepository){
    fun saveLog(logEntry: LogEntry) {
        logRepository.addNewLog(logEntry)
    }
}