package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.LogEntryCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.logic.model.LogEntry

class CsvLogDataSource(
    private val csvReader: CsvReader,
    private val logEntryCsvParser: LogEntryCsvParser,
    private val writeInFileUseCase: WriteInFileUseCase
) : LogDataSource {


    override suspend fun addNewLog(logEntry: LogEntry) {
        val csvLine = logEntryCsvParser.toCsvRow(logEntry) + "\n"
        writeInFileUseCase.writeLineToFile(LOGS_FILE,csvLine)
    }

    override suspend fun getLogByTargetId(targetId: String): List<LogEntry> {
        return getAllLogs().filter { it.targetId == targetId }
    }

    override suspend fun getAllLogs(): List<LogEntry> {
        return csvReader.read(LOGS_FILE).mapNotNull {
            try {
                logEntryCsvParser.fromCsvRow(it)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }

    override suspend fun deleteLogByTargetId(targetId: String) {
        val filteredLogs = getAllLogs().filterNot { it.targetId == targetId }
        val csvLines = filteredLogs.map{log -> logEntryCsvParser.toCsvRow(log)}
        writeInFileUseCase.writeLinesToFile(LOGS_FILE, csvLines)
    }

    companion object {
        const val LOGS_FILE = "logs.csv"
    }
}