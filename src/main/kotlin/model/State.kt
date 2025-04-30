package org.qudus.squad.model

import org.qudus.squad.logic.GenerateUUID

data class State(
    val id: String = GenerateUUID().generate(),
    val name: String,
)
