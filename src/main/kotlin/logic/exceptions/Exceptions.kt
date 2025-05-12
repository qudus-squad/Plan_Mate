package logic.exceptions

import logic.exceptions.ExceptionsUtils.Companion.FAILED_CREATING_TASK_STATE
import logic.exceptions.ExceptionsUtils.Companion.TASK_STATE_NOT_FOUND
import logic.exceptions.ExceptionsUtils.Companion.USER_ALREADY_EXIST
import logic.exceptions.ExceptionsUtils.Companion.USER_NOT_FOUND


open class InvalidTaskDataException(message: String) : Exception(message)

class InvalidTaskIdException(message: String) : InvalidTaskDataException(message)
class InvalidTaskTitleException(message: String) : InvalidTaskDataException(message)
class InvalidTaskDescriptionException(message: String) : InvalidTaskDataException(message)
class InvalidTaskCreatorUserIdException(message: String) : InvalidTaskDataException(message)
class InvalidTaskProjectIdException(message: String) : InvalidTaskDataException(message)
class InvalidTaskStateNameException(message: String) : InvalidTaskDataException(message)
open class InvalidUserDataException(message: String) : Exception(message)
class InvalidUserNameException(message: String) : InvalidUserDataException(message)
class InvalidPasswordException(message: String) : InvalidUserDataException(message)
class InvalidUserIdException(message: String) : InvalidUserDataException(message)
class UserNotFoundException(message: String = USER_NOT_FOUND) : Exception(message)
class AccessDeniedException(message: String) : Exception(message)
class UserAlreadyExistsException(message: String = USER_ALREADY_EXIST) : Exception(message)
open class InvalidProjectData(message: String) : Exception(message)
class InvalidProjectTitleException(message: String) : InvalidProjectData(message)
class InvalidProjectDescriptionException(message: String) : InvalidProjectData(message)
class InvalidProjectIDException(message: String) : InvalidProjectData(message)
class TaskNotFoundException(message: String) : Exception(message)
class ProjectNotFoundException(message: String) : Exception(message)
class FailedCreatingProject(message: String) : Exception(message)
class FailedCreatingTaskStateException(message: String = FAILED_CREATING_TASK_STATE) : Exception(message)
class TaskStateNotFoundException(message: String = TASK_STATE_NOT_FOUND) : Exception(message)
class InvalidLogUserNameException(message: String) : Exception(message)
class InvalidLogTargetIdException(message: String) : Exception(message)
class InvalidLogActionException(message: String) : Exception(message)

class ExceptionsUtils {

    companion object {
        const val USER_ALREADY_EXIST = "User Already Exists"
        const val USER_NOT_FOUND = "there is no user with selected id"
        const val FAILED_CREATING_PROJECT = "Failed to create project"
        const val FAILED_CREATING_TASK_STATE = "Failed to create project"
        const val TASK_STATE_NOT_FOUND = "Task state not found"
    }
}