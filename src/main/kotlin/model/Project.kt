package org.qudus.squad.model

import java.time.LocalDateTime
import java.util.UUID

data class Project(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val creatorUserId: UUID,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val lastUpdateAt: LocalDateTime = LocalDateTime.now(),
    val states: List<State>,
)