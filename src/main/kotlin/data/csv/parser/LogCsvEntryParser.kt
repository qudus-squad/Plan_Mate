package org.qudus.squad.data.csv.parser

import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.model.LogEntry
import org.qudus.squad.model.TargetType

class LogCsvEntryParser : CsvParser<LogEntry> {
    override fun fromCsvRow(row: String): LogEntry {
        val cleanedLine = row.trim().removeSuffix("\n")
        val LogEntryList = cleanedLine.split(',')
        return LogEntry(
            userName = LogEntryList[LogEntryCsvColumnIndex.USERNAME.index],
            targetId = LogEntryList[LogEntryCsvColumnIndex.TARGET_ID.index],
            targetType = TargetType.valueOf(LogEntryList[LogEntryCsvColumnIndex.TARGET_TYPE.index]),
            action = LogEntryList[LogEntryCsvColumnIndex.ACTION.index],
            oldValue = LogEntryList[LogEntryCsvColumnIndex.OLD_VALUE.index],
            newValue = LogEntryList[LogEntryCsvColumnIndex.NEW_VALUE.index],
            loggedAt = LogEntryList[LogEntryCsvColumnIndex.LOGGED_AT.index].toLocalDateTime()
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
