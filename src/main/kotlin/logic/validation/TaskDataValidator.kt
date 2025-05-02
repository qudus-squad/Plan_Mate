package org.qudus.squad.logic.validation

import org.qudus.squad.model.entity.Task

class TaskDataValidator {

    fun validateTaskValues(task: Task): Boolean{
        return task.title.isNotBlank() && task.description.isNotBlank()
                && task.creatorUserID.isNotBlank() && task.projectId.isNotBlank()
                && task.taskState.name.isNotBlank()
    }

    fun validateDeleteTaskValues(userName: String, taskId: String,taskName: String): Boolean{
        return userName.isNotBlank() && taskId.isNotBlank() && taskName.isNotBlank()
    }
}