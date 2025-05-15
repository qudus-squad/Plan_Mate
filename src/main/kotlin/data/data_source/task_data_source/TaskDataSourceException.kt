package org.qudus.squad.data.data_source.task_data_source

open class InvalidTaskDataBase(message: String) : Exception(message)
class InvalidToAddTaskException(message: String) : InvalidTaskDataBase(message)
class InvalidToGetTaskByIdTaskException(message: String) : InvalidTaskDataBase(message)
class InvalidToGetAllTasksException(message: String) : InvalidTaskDataBase(message)
class InvalidToDeleteTaskException(message: String) : InvalidTaskDataBase(message)
class InvalidToEditTaskException(message: String) : InvalidTaskDataBase(message)

const val FAILED_ADD_TASK = "failed to add task that`s caused when add task to mongo database"
const val FAILED_GET_TASK_BY_ID = "task not found that`s caused when get task by id from mongo database"
const val FAILED_GET_ALL_TASKS = "tasks not found that`s caused when get all tasks from mongo database"
const val FAILED_DELETE_TASK = "failed to delete task that`s caused when delete task from mongo database"
const val FAILED_EDIT_TASK = "failed to edit task that`s caused when edit task from mongo database"
