package org.qudus.squad.logic.model

import org.qudus.squad.ui.utils.GenerateUUID

data class TaskState(
    val id: String = GenerateUUID().generate(),
    val name: String,
)
