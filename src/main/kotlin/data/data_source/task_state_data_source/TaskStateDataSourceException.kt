package org.qudus.squad.data.data_source.task_state_data_source


open class InvalidTaskStateDataBase(message: String) : Exception(message)
class InvalidToAddTaskStateException(message: String) : InvalidTaskStateDataBase(message)
class InvalidToGetByIdTaskStateException(message: String) : InvalidTaskStateDataBase(message)
class InvalidToGetAllTaskStatesException(message: String) : InvalidTaskStateDataBase(message)
class InvalidToDeleteTaskStateException(message: String) : InvalidTaskStateDataBase(message)
class InvalidToEditTaskStateException(message: String) : InvalidTaskStateDataBase(message)

const val FAILED_ADD_TASK_STATE = "failed to add task state that's caused when adding to mongo database"
const val FAILED_GET_TASK_STATE_BY_ID = "task state not found that's caused when getting by id from mongo database"
const val FAILED_GET_ALL_TASK_STATES = "task states not found that's caused when getting all task states from mongo database"
const val FAILED_DELETE_TASK_STATE = "failed to delete task state that's caused when deleting from mongo database"
const val FAILED_EDIT_TASK_STATE = "failed to edit task state that's caused when editing from mongo database"
