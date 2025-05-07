package org.qudus.squad.data.data_source.task_data_source.remote

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Task

data class TaskDto(
    val id: String = GenerateUUID().generate(),
    val title: String,
    val description: String,
    val projectId: String,
    val taskState: TaskStateDto,
    val creatorUserID: String = GenerateUUID().generate(),
    val assignedUserId: String? = GenerateUUID().generate(),
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val lastUpdatedAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
)

fun TaskDto.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        projectId = this.projectId,
        taskState = this.taskState.toTaskState(),
        creatorUserID = this.creatorUserID,
        assignedUserId = this.assignedUserId,
        createdAt = this.createdAt,
        lastUpdatedAt = this.lastUpdatedAt
    )
}

fun Task.toTaskDto(): TaskDto {
    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        projectId = this.projectId,
        taskState = this.taskState.toTaskStateDto(),
        creatorUserID = this.creatorUserID,
        assignedUserId = this.assignedUserId,
        createdAt = this.createdAt,
        lastUpdatedAt = this.lastUpdatedAt
    )
}