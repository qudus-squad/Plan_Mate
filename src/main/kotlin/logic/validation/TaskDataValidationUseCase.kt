package org.qudus.squad.logic.validation

import logic.exceptions.*
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

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
        validateTaskId(taskId)
        return true
    }

    fun validateTaskId(taskId: String): Boolean{
        if (!isValidTaskId(taskId)){
            throw InvalidTaskIdException(INVALID_TASK_ID)
        }
        return true
    }

    fun validateAssignTaskValues(userId : String, taskId : String): Boolean{
        if (!isValidUserID(userId)){
            throw InvalidUserIdException(UserDataValidationUseCase.INVALID_USER_ID)
        }
        if (!isValidTaskId(taskId)){
            throw InvalidUserNameException(UserDataValidationUseCase.INVALID_USER_NAME)
        }
        return true
    }
// i take it temporary form user data validator
    private fun isValidUserID(userid: String): Boolean {
        val formattedUserId = userid.trim()
        return formattedUserId.isNotEmpty() && userid.all { ch ->
            ch.isLetterOrDigit() || ch == '.' || ch == '_'
        }
    }
    fun validateEditTaskName(taskStateName: String){
        if (!isValidTaskStateName(taskStateName)){
            throw InvalidTaskStateNameException(INVALID_TASK_STATE_NAME)
        }
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