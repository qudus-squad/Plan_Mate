package org.qudus.squad.data.data_source.user_data_source


class InvalidToAddUserException : Exception()
class InvalidToGetByIdUserException : Exception()
class InvalidToGetAllUsersException : Exception()
class InvalidToDeleteUserException : Exception()
class InvalidToGetUserByProjectIdException : Exception()

const val FAILED_ADD_USER = "failed to add user that`s caused when adding user to mongo database"
const val FAILED_GET_USER_BY_ID = "user not found that`s caused when getting user by id from mongo database"
const val FAILED_GET_ALL_USERS = "users not found that`s caused when getting all users from mongo database"
const val FAILED_DELETE_USER = "failed to delete user that`s caused when deleting user from mongo database"
const val FAILED_GET_USER_BY_PROJECT_ID =
    "user not found that`s caused when getting user by project id from mongo database"
