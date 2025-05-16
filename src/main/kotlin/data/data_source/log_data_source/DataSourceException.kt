package org.qudus.squad.data.data_source.log_data_source

class InvalidToAddLogException(message: String = FAILED_ADD_LOG) : Exception(message)
class InvalidToGetByIdLogException(message: String = FAILED_GET_LOG_BY_ID) : Exception(message)
class InvalidToGetAllLogsException(message: String = FAILED_GET_ALL_LOGS) : Exception(message)
class InvalidToDeleteLogException(message: String = FAILED_DELETE_LOG) : Exception(message)

const val FAILED_ADD_LOG = "failed to add log"
const val FAILED_GET_LOG_BY_ID = "log not found"
const val FAILED_GET_ALL_LOGS = "logs not found"
const val FAILED_DELETE_LOG = "failed to delete"