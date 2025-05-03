package org.qudus.squad.logic.exceptions

class EmptyValuesException(message: String) : Exception(message)
class InvalidUserDataException(message: String) : Exception(message)
class AccessDeniedException(message: String) : Exception(message)
class UserAlreadyExistException(message: String) : Exception(message)
class UnknownException(message: String) : Exception(message)
class NoChangeHistoryFoundException(message: String) : Exception(message)
class UnauthorizedAccessException(message: String) : Exception(message)
class InvalidProjectInfo(message: String) : Exception(message)
class NoTasksFoundException(message : String) : Exception(message)