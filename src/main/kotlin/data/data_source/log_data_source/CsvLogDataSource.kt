package org.qudus.squad.data.data_source.log_data_source

import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.LogCsvParser
import org.qudus.squad.model.LogEntry
import java.util.UUID

class CsvLogDataSource(
    private val fileName: String,
    private val csvReader: CsvReader,
    private val logCsvParser: LogCsvParser
): LogDataSource {
    override fun saveLog(logEntry: LogEntry) {
        TODO("Not yet implemented")
    }

    override fun getLogByTargetId(targetId: UUID): List<LogEntry>? {
        TODO("Not yet implemented")
    }

    override fun getAllLogs(): List<LogEntry>? {
        TODO("Not yet implemented")
    }
}