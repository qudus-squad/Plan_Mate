package org.qudus.squad.ui

import logic.use_cases.tasks.AssignTaskToUserUseCase import org.qudus.squad.logic.exceptions.TaskNotFoundException
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.User
import java.io.IOException
import java.time.format.DateTimeParseException

class AssignTaskToUserUI(
    private val assignTaskToUserUseCase: AssignTaskToUserUseCase
) {
    fun assignTaskToUser(task: Task, user: User) {
        try {
            if (assignTaskToUserUseCase.assignTaskToUser(user.userId, task.id)) {
                print(TASK_ASSIGNED_SUCCESSFULLY)
            } else {
                print(FAILED_TO_ASSIGN_USER_TO_THIS_TASK)
            }
        } catch (_: IOException) {
            println("$FAILED_TO_ASSIGN_USER_TO_THIS_TASK  $IO_ERROR")
        } catch (_: FileSystemException) {
            println("$FAILED_TO_ASSIGN_USER_TO_THIS_TASK  $FILE_SYSTEM_ERROR")
        } catch (_: IllegalArgumentException) {
            println("$FAILED_TO_ASSIGN_USER_TO_THIS_TASK  $INVALID_ARGUMENT_ERROR")
        } catch (_: DateTimeParseException) {
            println("$FAILED_TO_ASSIGN_USER_TO_THIS_TASK  $DATE_TIME_PARSE_ERROR")
        } catch (_: AccessDeniedException) {
            println("$FAILED_TO_ASSIGN_USER_TO_THIS_TASK  $ACCESS_DENIED_ERROR")
        } catch (_: Exception) {
            println("$FAILED_TO_ASSIGN_USER_TO_THIS_TASK  $UNEXPECTED_ERROR")
        }catch (_: TaskNotFoundException) {
            println("$FAILED_TO_ASSIGN_USER_TO_THIS_TASK  $TASK_NOT_FOUND_ERROR")
        }

    }

    companion object {
        const val TASK_ASSIGNED_SUCCESSFULLY = "Task assigned to this user successfully"
        const val FAILED_TO_ASSIGN_USER_TO_THIS_TASK = "Failed to assign task to tis use.r"
        const val IO_ERROR = "An unexpected input/output error occurred. Please check the file system and try again."
        const val FILE_SYSTEM_ERROR =
            "A file system error occurred. Please ensure the file is accessible and try again."
        const val INVALID_ARGUMENT_ERROR = "The provided input is invalid. Please check the input values and try again."
        const val DATE_TIME_PARSE_ERROR =
            "There was an issue parsing the date and time. Please ensure the format is correct."
        const val ACCESS_DENIED_ERROR = "Access to the requested resource is denied. Please check your permissions."
        const val UNEXPECTED_ERROR = "An unexpected error occurred. Please try again later or contact support."
        const val TASK_NOT_FOUND_ERROR = "The task you are trying to assign was not found. Please check the task ID."

    }


}