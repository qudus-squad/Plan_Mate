package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.LogEntryCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.model.entity.LogEntry
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.appendText

class CsvLogDataSource(
    private val csvReader: CsvReader,
    private val logEntryCsvParser: LogEntryCsvParser,
    private val writeInFileUseCase: WriteInFileUseCase
) : LogDataSource {


    override fun addLog(logEntry: LogEntry) {
        val csvLine = logEntryCsvParser.toCsvRow(logEntry) + "\n"
        writeInFileUseCase.writeLineToFile(LOGS_FILE,csvLine)
    }

    override fun getLogByTargetId(targetId: String): List<LogEntry>? {
        val filteredLogs = getAllLogs().filter { it.targetId == targetId }
        return filteredLogs.ifEmpty { null }
    }

    override fun getAllLogs(): List<LogEntry> {
        return csvReader.read(LOGS_FILE).mapNotNull {
            try {
                logEntryCsvParser.fromCsvRow(it)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }

    override fun deleteLogByTargetId(targetId: String) {
        val filteredLogs = getAllLogs().filterNot { it.targetId == targetId }
        val csvLines = filteredLogs.map{log -> logEntryCsvParser.toCsvRow(log)}
        writeInFileUseCase.writeLinesToFile(LOGS_FILE, csvLines)
    }

    companion object {
        const val LOGS_FILE = "logs.csv"
    }
}