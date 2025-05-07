package org.qudus.squad.data.data_source.task_data_source.remote

import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.TaskState

data class TaskStateDto(
    val id: String = GenerateUUID().generate(),
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