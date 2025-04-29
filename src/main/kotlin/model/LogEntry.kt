package org.qudus.squad.model

import java.time.LocalDate
import java.util.UUID


data class LogEntry(
    val userName: String,
    val targetId: UUID,
    val targetType: TargetType,
    val action: String,
    val oldValue: String?,
    val newValue: String?,
    val loggedAt: LocalDate = LocalDate.now()
)

enum class TargetType {
    PROJECT, TASK
}