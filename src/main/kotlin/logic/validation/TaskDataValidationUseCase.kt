package org.qudus.squad.logic.validation

import model.exceptions.InvalidTaskCreatorUserIdException
import model.exceptions.InvalidTaskDescriptionException
import model.exceptions.InvalidTaskIdException
import model.exceptions.InvalidTaskProjectIdException
import model.exceptions.InvalidTaskStateNameException
import model.exceptions.InvalidTaskTitleException
import model.exceptions.InvalidUserNameException
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

    fun validateDeleteTaskValues(userName: String, taskId: String,taskTitle: String): Boolean{
        if (!isValidTaskTitle(taskTitle)){
            throw InvalidTaskTitleException(INVALID_TASK_TITLE)
        }
        if (!isValidUserName(userName)){
            throw InvalidUserNameException(UserDataValidationUseCase.INVALID_USER_NAME)
        }
        if (!isValidTaskId(taskId)){
            throw InvalidTaskIdException(INVALID_TASK_ID)
        }
        return true
    }

    private fun isValidUserName(userName: String): Boolean{
        return userName.isNotBlank()
    }

    private fun isValidTaskId(taskId: String): Boolean{
        return taskId.isNotBlank()
    }

    private fun isValidTaskTitle(title: String): Boolean{
        return title.isNotBlank()
    }

    private fun isValidTaskDescription(description:String): Boolean{
        return description.isNotBlank()
    }

    private fun isValidTaskCreatorId(creatorId: String): Boolean{
        return creatorId.isNotBlank()
    }

    private fun isValidTaskProjectId(projectId: String): Boolean{
        return projectId.isNotBlank()
    }

    private fun isValidTaskStateName(stateName: String): Boolean{
        return stateName.isNotBlank()
    }

    companion object{
        const val INVALID_TASK_ID = "Task id should not be empty or blank."
        const val INVALID_TASK_TITLE = "Title should not be empty or blank."
        const val INVALID_TASK_DESCRIPTION = "Description should not be empty or blank."
        const val INVALID_TASK_CREATOR_ID = "Task creator id should not be empty or blank."
        const val INVALID_TASK_PROJECT_ID = "Task project id should not be empty or blank."
        const val INVALID_TASK_STATE_NAME = "Task state name should not be empty or blank."
    }
}