package org.qudus.squad.model.entity

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime

data class LogEntry(
    val userName: String,
    val targetId: String,
    val targetType: TargetType,
    val action: String,
    val oldValue: String? = null,
    val newValue: String? = null,
    val loggedAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
)

enum class TargetType {
    PROJECT, TASK, USER
}