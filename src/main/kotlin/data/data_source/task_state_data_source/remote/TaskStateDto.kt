package org.qudus.squad.data.data_source.task_state_data_source.remote

import org.qudus.squad.logic.model.TaskState

data class TaskStateDto(
    val id: String,
    val name: String,
)

fun TaskState.toTaskStateDto(): TaskStateDto {
    return TaskStateDto(
        id = this.id,
        name = this.name
    )
}

fun TaskStateDto.toTaskState(): TaskState {
    return TaskState(
        id = this.id,
        name = this.name
    )
}