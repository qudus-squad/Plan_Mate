package org.qudus.squad.data.csv

import org.qudus.squad.model.LogEntry

class LogCsvParser: CsvParser<LogEntry> {
    override fun fromCsvRow(row: List<String>): LogEntry {
        TODO("Not yet implemented")
    }

    override fun toCsvRow(model: LogEntry): String {
        TODO("Not yet implemented")
    }
}