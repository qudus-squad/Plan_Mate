package org.qudus.squad.data.data_source.project_data_source

class InvalidToAddProjectException(message: String = FAILED_ADD_PROJECT) : Exception(message)
class InvalidToGetByIdProjectException(message: String = FAILED_GET_PROJECT_BY_ID) : Exception(message)
class InvalidToGetAllLException(message: String = FAILED_GET_ALL_PROJECTS) : Exception(message)
class InvalidToDeleteProjectException(message: String = FAILED_DELETE_PROJECT) : Exception(message)
class InvalidToEditProjectException(message: String = FAILED_EDIT_PROJECT) : Exception(message)

const val FAILED_ADD_PROJECT = "failed to add project"
const val FAILED_GET_PROJECT_BY_ID = "project not found"
const val FAILED_GET_ALL_PROJECTS = "Projects not found"
const val FAILED_DELETE_PROJECT = "project not found"
const val FAILED_EDIT_PROJECT = "failed to delete project"