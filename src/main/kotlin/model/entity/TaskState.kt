package org.qudus.squad.model.entity

import org.qudus.squad.logic.utils.GenerateUUID

data class TaskState(
    val id: String = GenerateUUID().generate(),
    val name: String,
)
