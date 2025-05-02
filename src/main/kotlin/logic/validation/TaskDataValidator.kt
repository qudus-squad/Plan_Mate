package org.qudus.squad.logic.validation

import org.qudus.squad.model.entity.Task

class TaskDataValidator {

    companion object{
        internal fun validateTaskValues(task: Task): Boolean{
            return task.title.isNotBlank() && task.description.isNotBlank()
                    && task.creatorUserID.isNotBlank() && task.projectId.isNotBlank()
                    && task.taskState.name.isNotBlank()
        }
    }
}