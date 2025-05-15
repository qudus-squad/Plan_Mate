package org.qudus.squad.data.data_source.task_data_source

class InvalidToAddTaskException : Exception()
class InvalidToGetTaskByIdTaskException : Exception()
class InvalidToGetAllTasksException: Exception()
class InvalidToDeleteTaskException : Exception()
class InvalidToEditTaskException : Exception()
class NoFoundTaskException : Exception()


const val FAILED_ADD_TASK = "failed to add task that`s caused when add task to mongo database"
const val FAILED_GET_TASK_BY_ID = "task not found that`s caused when get task by id from mongo database"
const val FAILED_DELETE_TASK = "failed to delete task that`s caused when delete task from mongo database"
const val FAILED_EDIT_TASK = "failed to edit task that`s caused when edit task from mongo database"
const val FAILED_ASSIGN_TASK = "failed to assign task to user that`s caused when assigning user ID to task in mongo database"
const val FAILED_UNASSIGN_TASK = "failed to switch task state (unassign) that`s caused when removing assigned user from task in mongo database"
const val FAILED_TO_FIND_TASK = "failed to add task that`s caused when add task to mongo database"
