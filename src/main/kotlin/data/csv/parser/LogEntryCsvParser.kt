package org.qudus.squad.data.csv.parser

import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.model.LogEntry
import org.qudus.squad.model.TargetType

class LogEntryCsvParser : CsvParser<LogEntry> {
    override fun fromCsvRow(row: String): LogEntry {
        val cleanedLine = row.trim().removeSuffix("\n")
        val logEntryList = cleanedLine.split(',')
        return LogEntry(
            userName = logEntryList[LogEntryCsvColumnIndex.USERNAME.index],
            targetId = logEntryList[LogEntryCsvColumnIndex.TARGET_ID.index],
            targetType = TargetType.valueOf(logEntryList[LogEntryCsvColumnIndex.TARGET_TYPE.index]),
            action = logEntryList[LogEntryCsvColumnIndex.ACTION.index],
            oldValue = logEntryList[LogEntryCsvColumnIndex.OLD_VALUE.index],
            newValue = logEntryList[LogEntryCsvColumnIndex.NEW_VALUE.index],
            loggedAt = logEntryList[LogEntryCsvColumnIndex.LOGGED_AT.index].toLocalDateTime()
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
