package org.qudus.squad.data.data_source.task_state_data_source


class InvalidToAddTaskStateException(message: String = FAILED_ADD_TASK_STATE) : Exception(message)
class InvalidToGetByIdTaskStateException(message: String = FAILED_GET_TASK_STATE_BY_ID) : Exception(message)
class InvalidToGetAllTaskStatesException(message: String = FAILED_GET_ALL_TASKS_STATES) : Exception(message)
class InvalidToDeleteTaskStateException(message: String = FAILED_DELETE_TASK_STATE) : Exception(message)
class InvalidToEditTaskStateException(message: String = FAILED_EDIT_TASK_STATE) : Exception(message)

const val FAILED_ADD_TASK_STATE = "failed to add task state"
const val FAILED_GET_TASK_STATE_BY_ID = "task state not found"
const val FAILED_GET_ALL_TASKS_STATES = "task states not found"
const val FAILED_DELETE_TASK_STATE = "failed to delete task state"
const val FAILED_EDIT_TASK_STATE = "failed to edit task state"
