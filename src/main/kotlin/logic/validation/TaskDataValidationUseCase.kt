package org.qudus.squad.logic.validation

import org.qudus.squad.logic.exceptions.InvalidTaskCreatorUserIdException
import org.qudus.squad.logic.exceptions.InvalidTaskDescriptionException
import org.qudus.squad.logic.exceptions.InvalidTaskProjectIdException
import org.qudus.squad.logic.exceptions.InvalidTaskStateNameException
import org.qudus.squad.logic.exceptions.InvalidTaskTitleException
import org.qudus.squad.model.entity.Task

class TaskDataValidationUseCase {

    fun validateTaskValues(task: Task): Boolean{
        if (!isValidTaskTitle(task.title)){
            throw InvalidTaskTitleException(INVALID_TASK_TITLE)
        }
        if (!isValidTaskDescription(task.description)){
            throw InvalidTaskDescriptionException(INVALID_TASK_DESCRIPTION)
        }
        if (!isValidTaskCreatorId(task.creatorUserID)){
            throw InvalidTaskCreatorUserIdException(INVALID_TASK_CREATOR_ID)
        }
        if (!isValidTaskProjectId(task.projectId)){
            throw InvalidTaskProjectIdException(INVALID_TASK_PROJECT_ID)
        }
        if (!isValidTaskStateName(task.taskState.name)){
            throw InvalidTaskStateNameException(INVALID_TASK_STATE_NAME)
        }
        return true
    }

    fun validateDeleteTaskValues(userName: String, taskId: String,taskName: String): Boolean{
        return userName.isNotBlank() && taskId.isNotBlank() && taskName.isNotBlank()
    }

    fun isValidTaskTitle(title: String): Boolean{
        return title.isNotBlank()
    }

    fun isValidTaskDescription(description:String): Boolean{
        return description.isNotBlank()
    }

    fun isValidTaskCreatorId(creatorId: String): Boolean{
        return creatorId.isNotBlank()
    }

    fun isValidTaskProjectId(projectId: String): Boolean{
        return projectId.isNotBlank()
    }

    fun isValidTaskStateName(stateName: String): Boolean{
        return stateName.isNotBlank()
    }

    companion object{
        const val INVALID_TASK_TITLE = "Title should not be empty or blank."
        const val INVALID_TASK_DESCRIPTION = "Description should not be empty or blank."
        const val INVALID_TASK_CREATOR_ID = "Task creator id should not be empty or blank."
        const val INVALID_TASK_PROJECT_ID = "Task project id should not be empty or blank."
        const val INVALID_TASK_STATE_NAME = "Task state name should not be empty or blank."
    }
}