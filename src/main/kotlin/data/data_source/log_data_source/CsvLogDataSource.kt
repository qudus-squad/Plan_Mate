package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.LogCsvEntryParser
import org.qudus.squad.model.LogEntry

class CsvLogDataSource(
    private val fileName: String,
    private val csvReader: CsvReader,
    private val logCsvEntryParser: LogCsvEntryParser
): LogDataSource {
    override fun saveLog(logEntry: LogEntry) {
        TODO("Not yet implemented")
    }

    override fun getLogByTargetId(targetId: String): List<LogEntry>? {
        TODO("Not yet implemented")
    }

    override fun getAllLogs(): List<LogEntry>? {
        TODO("Not yet implemented")
    }
}