package org.qudus.squad.model

import java.util.UUID

data class State(
    val id: UUID = UUID.randomUUID(),
    val name: String,
)