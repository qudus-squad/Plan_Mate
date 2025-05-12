package org.qudus.squad.logic.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.ui.utils.GenerateUUID

data class Project(
    val id: String = GenerateUUID().generate(),
    val title: String,
    val description: String,
    val creatorUserId:String,
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val lastUpdateAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val tasks: List<Task> = emptyList(),
    val taskState : List<TaskState> = emptyList(),
)
