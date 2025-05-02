package org.qudus.squad.data.data_source.log_data_source

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.LogEntryCsvParser
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource.Companion.PROJECTS_FILE
import org.qudus.squad.model.entity.LogEntry

class CsvLogDataSource(
    private val csvReader: CsvReader, private val logEntryCsvParser: LogEntryCsvParser
) : LogDataSource {

    override fun addLog(logEntry: LogEntry) {
        val csvLine = logEntryCsvParser.toCsvRow(logEntry)
        FileSystem.SYSTEM.appendingSink(PROJECTS_FILE.toPath()).buffer().use { sink ->
            sink.writeUtf8(csvLine + "\n")
        }
    }

    override fun getLogByTargetId(targetId: String): List<LogEntry>? {
        val filteredLogs = getAllLogs().filter { log -> log.targetId == targetId }
        return filteredLogs.ifEmpty { null }
    }

    override fun getAllLogs(): List<LogEntry> {
        return csvReader.read(LOGS_FILE).mapNotNull { line ->
            try {
                logEntryCsvParser.fromCsvRow(line)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    override fun deleteLogByTargetId(targetId: String) {
        val logs = getAllLogs().filterNot { it.targetId == targetId }
        FileSystem.SYSTEM.sink(PROJECTS_FILE.toPath()).buffer().use { sink ->
            logs.forEach { log ->
                val csvLine = logEntryCsvParser.toCsvRow(log)
                sink.writeUtf8(csvLine + "\n")
            }
        }
    }

    companion object {
        const val LOGS_FILE = "logs.csv"
    }
}