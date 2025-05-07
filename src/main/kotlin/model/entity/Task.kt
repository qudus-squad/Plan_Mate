package org.qudus.squad.model.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.ui.utils.GenerateUUID

data class Task(
    val id : String = GenerateUUID().generate(),
    val title :String,
    val description : String,
    val projectId: String,
    val taskState: TaskState,
    val creatorUserID: String = GenerateUUID().generate(),
    val assignedUserId : String? = GenerateUUID().generate(),
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val lastUpdatedAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
)