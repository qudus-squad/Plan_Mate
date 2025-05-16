package org.qudus.squad.data.data_source.user_data_source


class InvalidToAddUserException(message: String = FAILED_ADD_USER) : Exception(message)
class InvalidToGetByIdUserException(message: String = FAILED_GET_USER_BY_ID) : Exception(message)
class InvalidToGetAllUsersException(message: String = FAILED_GET_ALL_USERS) : Exception(message)
class InvalidToDeleteUserException(message: String = FAILED_DELETE_USER) : Exception(message)
class InvalidToGetUserByProjectIdException(message: String = FAILED_GET_USER_BY_PROJECT_ID) : Exception(message)

const val FAILED_ADD_USER = "failed to add user"
const val FAILED_GET_USER_BY_ID = "user not found"
const val FAILED_GET_ALL_USERS = "users not found"
const val FAILED_DELETE_USER = "failed to delete user"
const val FAILED_GET_USER_BY_PROJECT_ID = "user not found"