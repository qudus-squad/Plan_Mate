package org.qudus.squad.data.data_source.project_data_source

open class InvalidProjectDataBase(message: String) : Exception(message)
class InvalidToAddProjectException(message: String) : InvalidProjectDataBase(message)
class InvalidToGetByIdProjectException(message: String) : InvalidProjectDataBase(message)
class InvalidToGetAllLException(message: String) : InvalidProjectDataBase(message)
class InvalidToDeleteProjectException(message: String) : InvalidProjectDataBase(message)
class InvalidToEditProjectException(message: String) : InvalidProjectDataBase(message)

const val FAILED_ADD_PROJECT = "failed to add project a that`s caused when add project to mongo database"
const val FAILED_GET_PROJECT_BY_ID = "project not found that`s caused when get project by id form mongo database"
const val FAILED_GET_ALL_PROJECTS = "Projects not found that`s caused when get all projects form mongo database"
const val FAILED_DELETE_PROJECT = "project not found that`s caused when delete project form mongo database"
const val FAILED_EDIT_PROJECT = "failed to delete project that`s caused when edit project from mongo database"