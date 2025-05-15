package org.qudus.squad.data.data_source.log_data_source

open class InvalidLogDataBase(message: String) : Exception(message)
class InvalidToAddLogException(message: String) : InvalidLogDataBase(message)
class InvalidToGetByIdLogException(message: String) : InvalidLogDataBase(message)
class InvalidToGetAllLogsException(message: String) : InvalidLogDataBase(message)
class InvalidToDeleteLogException(message: String) : InvalidLogDataBase(message)

const val FAILED_ADD_LOG = "failed to add log a that`s caused when add to mongo database"
const val FAILED_GET_LOG_BY_ID = "log not found that`s caused when get form mongo database"
const val FAILED_GET_ALL_LOGS = "logs not found that`s caused when get form mongo database"
const val FAILED_DELETE_LOG = "failed to delete log that`s caused when delete from mongo database"