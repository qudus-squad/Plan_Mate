package org.qudus.squad.model

import java.time.LocalDateTime
import java.util.UUID

data class Task(
    val id : UUID = UUID.randomUUID(),
    val title :String,
    val description : String,
    val projectId: UUID,
    val state: State,
    val creatorUserID: UUID,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val lastUpdatedAt: LocalDateTime = LocalDateTime.now(),
)
