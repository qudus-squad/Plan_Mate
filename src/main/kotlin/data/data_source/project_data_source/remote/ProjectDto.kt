package org.qudus.squad.data.data_source.project_data_source.remote

import kotlinx.datetime.LocalDateTime
import org.qudus.squad.data.data_source.task_data_source.remote.*
import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.TaskState

data class ProjectDto(
    val id: String = GenerateUUID().generate(),
    val title: String,
    val description: String,
    val creatorUserId: String,
    val createdAt: String,
    val lastUpdateAt: String,
    val tasks: List<TaskDto> = emptyList()
)

fun ProjectDto.toProject(): Project {
    return Project(
        id = this.id,
        title = this.title,
        description = this.description,
        creatorUserId = this.creatorUserId,
        createdAt = LocalDateTime.parse(this.createdAt),
        lastUpdateAt = LocalDateTime.parse(this.lastUpdateAt),
        tasks = this.tasks.map { it.toTask() }
    )
}

fun Project.toProjectDto(): ProjectDto {
    return ProjectDto(
        id = this.id,
        title = this.title,
        description = this.description,
        creatorUserId = this.creatorUserId,
        createdAt = this.createdAt.toString(),
        lastUpdateAt = this.lastUpdateAt.toString(),
        tasks = this.tasks.map { it.toTaskDto() }
    )
}