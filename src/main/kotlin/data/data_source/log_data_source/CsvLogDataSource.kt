package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.LogEntryCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.model.entity.LogEntry

class CsvLogDataSource(
    private val csvReader: CsvReader,
    private val logEntryCsvParser: LogEntryCsvParser,
    private val writeInFileUseCase: WriteInFileUseCase
) : LogDataSource {


    override suspend fun addNewLog(logEntry: LogEntry): LogEntry {
        return try {
            val csvLine = logEntryCsvParser.toCsvRow(logEntry) + "\n"
            writeInFileUseCase.writeLineToFile(LOGS_FILE, csvLine)
            logEntry
        } catch (e: Exception) {
            throw InvalidToAddLogException(FAILED_ADD_LOG)
        }
    }

    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
        return getAllLogs().filter { it.targetId == targetId }
    }

    override suspend fun getAllLogs(): List<LogEntry> {
        return csvReader.read(LOGS_FILE).map {
            try {
                logEntryCsvParser.fromCsvRow(it)
            } catch (_: IllegalArgumentException) {
                throw InvalidToGetAllLogsException(FAILED_GET_ALL_LOGS)
            }
        }
    }

    override suspend fun deleteLogByTargetId(targetId: String): Boolean {
        return try {
            val filteredLogs = getAllLogs().filterNot { it.targetId == targetId }
            val csvLines = filteredLogs.map { log -> logEntryCsvParser.toCsvRow(log) }
            writeInFileUseCase.writeLinesToFile(LOGS_FILE, csvLines)
            true
        } catch (e: Exception) {
            throw InvalidToDeleteLogException(FAILED_DELETE_LOG)
        }
    }

    companion object {
        const val LOGS_FILE = "logs.csv"
    }
}