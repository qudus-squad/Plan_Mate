package org.qudus.squad.data.data_source.task_data_source.remote



import kotlinx.datetime.LocalDateTime
import org.qudus.squad.data.data_source.task_state_data_source.remote.TaskStateDto
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

data class TaskDto(
    val id : String,
    val title :String,
    val description : String,
    val projectId: String,
    val taskState: TaskStateDto,
    val creatorUserID: String,
    val assignedUserId : String?,
    val createdAt: String,
    val lastUpdatedAt: String,
)

fun Task.toTaskDto(): TaskDto{
    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        projectId = this.projectId,
        taskState = TaskStateDto(this.taskState.id, this.taskState.name),
        creatorUserID = this.creatorUserID,
        assignedUserId = this.assignedUserId,
        createdAt = this.createdAt.toString(),
        lastUpdatedAt = this.lastUpdatedAt.toString()
    )
}

fun TaskDto.toTask(): Task{
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        projectId = this.projectId,
        taskState = TaskState(id= this.taskState.id,name = this.taskState.name),
        creatorUserID = this.creatorUserID,
        assignedUserId = this.assignedUserId,
        createdAt = LocalDateTime.parse(this.createdAt),
        lastUpdatedAt = LocalDateTime.parse(this.lastUpdatedAt)
    )
}
