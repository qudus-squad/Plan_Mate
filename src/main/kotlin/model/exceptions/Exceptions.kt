package org.qudus.squad.model.exceptions


class InvalidUserDataException(message: String) : Exception(message)
class AccessDeniedException(message: String) : Exception(message)
class UserAlreadyExistException(message: String) : Exception(message)
class UnknownException(message: String) : Exception(message)
class NoChangeHistoryFoundException(message: String) : Exception(message)
class UnauthorizedAccessException(message: String) : Exception(message)
class InvalidProjectInfo(message: String) : Exception(message)