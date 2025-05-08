package org.qudus.squad.data.data_source.task_data_source.remote



import kotlinx.datetime.LocalDateTime
import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

data class TaskDto(
    val id : String = GenerateUUID().generate(),
    val title :String,
    val description : String,
    val projectId: String,
    val taskState: TaskStateDto,
    val creatorUserID: String = GenerateUUID().generate(),
    val assignedUserId : String?,
    val createdAt: String,
    val lastUpdatedAt: String,
){
    companion object{
        const val TASK_ID = "id"
        const val TASK_TITLE = "title"
        const val TASK_DESCRIPTION = "description"
        const val TASK_PROJECT_ID = "projectId"
        const val TASK_CREATOR_ID = "creatorUserID"
        const val TASK_ASSIGNED_USER_ID = "assignedUserId"
        const val TASK_CREATED_AT = "createdAt"
        const val TASK_LAST_UPDATED_AT = "lastUpdatedAt"
    }
}

fun Task.toTaskDto(): TaskDto{
    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        projectId = this.projectId,
        taskState = TaskStateDto(this.taskState.id,this.taskState.name),
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
