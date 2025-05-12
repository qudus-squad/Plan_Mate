package org.qudus.squad.data.csv.parser

import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.TargetType

class LogEntryCsvParser : CsvParser<LogEntry> {
    override fun fromCsvRow(row: String): LogEntry {
        val cleanedLine = row.trim().removeSuffix("\n")
        val logEntryList = cleanedLine.split(',')
        return LogEntry(
            userName = logEntryList[LogEntryCsvColumnIndex.USERNAME],
            targetId = logEntryList[LogEntryCsvColumnIndex.TARGET_ID],
            targetType = TargetType.valueOf(logEntryList[LogEntryCsvColumnIndex.TARGET_TYPE]),
            action = logEntryList[LogEntryCsvColumnIndex.ACTION],
            oldValue = logEntryList[LogEntryCsvColumnIndex.OLD_VALUE],
            newValue = logEntryList[LogEntryCsvColumnIndex.NEW_VALUE],
            loggedAt = logEntryList[LogEntryCsvColumnIndex.LOGGED_AT].toLocalDateTime()
        )
    }

    override fun toCsvRow(model: LogEntry): String {
        return listOf(
            model.userName,
            model.targetId,
            model.targetType,
            model.action,
            model.oldValue,
            model.newValue,
            model.loggedAt,
        ).joinToString(",")
    }
}
