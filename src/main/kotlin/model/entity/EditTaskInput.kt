package org.qudus.squad.model.entity

data class EditTaskInput(
    val userName: String, val updatedTask: Task, val action: String, val oldValue: String, val newValue: String
)