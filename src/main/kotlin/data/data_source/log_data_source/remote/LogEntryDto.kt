package org.qudus.squad.data.data_source.log_data_source.remote

import kotlinx.datetime.LocalDateTime
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.TargetType



data class LogEntryDto(
    val userName: String,
    val targetId: String,
    val targetType: String,
    val action: String,
    val oldValue: String? = null,
    val newValue: String? = null,
    val loggedAt: String
)

fun LogEntry.toLogEntryDto(): LogEntryDto {
    return LogEntryDto(
        userName = this.userName,
        targetId = this.targetId,
        targetType = when (this.targetType) {
            TargetType.TASK -> "TASK"
            TargetType.PROJECT -> "PROJECT"
            TargetType.USER-> "USER"
        },
        action = this.action,
        oldValue = this.oldValue,
        newValue = this.newValue,
        loggedAt = this.loggedAt.toString()
    )
}

fun LogEntryDto.toLogEntry(): LogEntry{
    return LogEntry(
        userName = this.userName,
        targetId = this.targetId,
        targetType = when (this.targetType) {
            "PROJECT" -> TargetType.PROJECT
            else -> TargetType.TASK
        },
        action = this.action,
        oldValue = this.oldValue,
        newValue = this.newValue,
        loggedAt = LocalDateTime.parse(this.loggedAt),
    )
}