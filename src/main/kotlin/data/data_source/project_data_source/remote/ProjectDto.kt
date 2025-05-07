package org.qudus.squad.data.data_source.project_data_source.remote

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Project
import org.qudus.squad.data.data_source.task_data_source.remote.TaskDto
import org.qudus.squad.data.data_source.task_data_source.remote.toTask
import org.qudus.squad.data.data_source.task_data_source.remote.toTaskDto

data class ProjectDto(
    val id: String = GenerateUUID().generate(),
    val title: String,
    val description: String,
    val creatorUserId: String = GenerateUUID().generate(),
    val createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val lastUpdateAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val tasks: List<TaskDto> = emptyList()
)

fun ProjectDto.toProject(): Project {
    return Project(
        id = this.id,
        title = this.title,
        description = this.description,
        creatorUserId = this.creatorUserId,
        createdAt = this.createdAt,
        lastUpdateAt = this.lastUpdateAt,
        tasks = this.tasks.map { it.toTask() }
    )
}

fun Project.toProjectDto(): ProjectDto {
    return ProjectDto(
        id = this.id,
        title = this.title,
        description = this.description,
        creatorUserId = this.creatorUserId,
        createdAt = this.createdAt,
        lastUpdateAt = this.lastUpdateAt,
        tasks = this.tasks.map { it.toTaskDto() }
    )
}