package org.qudus.squad.data.data_source.task_data_source


class InvalidToAddTaskException(message: String = FAILED_ADD_TASK) : Exception(message)
class InvalidToGetTaskByIdTaskException(message: String = FAILED_GET_TASK_BY_ID) : Exception(message)
class InvalidToGetAllTasksException(message: String = FAILED_GET_ALL_TASKS) : Exception(message)
class InvalidToDeleteTaskException(message: String = FAILED_DELETE_TASK) : Exception(message)
class InvalidToEditTaskException(message: String = FAILED_EDIT_TASK) : Exception(message)
class NoFoundTaskException(message: String = FAILED_TO_FIND_TASK) : Exception(message)


const val FAILED_ADD_TASK = "failed to add task"
const val FAILED_GET_TASK_BY_ID = "task not found"
const val FAILED_GET_ALL_TASKS = "tasks not found"
const val FAILED_DELETE_TASK = "failed to delete task"
const val FAILED_EDIT_TASK = "failed to edit task "
const val FAILED_ASSIGN_TASK = "failed to assign task to user"
const val FAILED_UNASSIGN_TASK = "failed to switch task state"
const val FAILED_TO_FIND_TASK = "failed to add task "
