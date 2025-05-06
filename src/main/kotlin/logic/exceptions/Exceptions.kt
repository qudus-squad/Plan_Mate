package org.qudus.squad.logic.exceptions


open class InvalidTaskDataException(message: String): Exception(message)

class InvalidTaskIdException(message: String): InvalidTaskDataException(message)
class InvalidTaskTitleException(message: String): InvalidTaskDataException(message)
class InvalidTaskDescriptionException(message: String): InvalidTaskDataException(message)
class InvalidTaskCreatorUserIdException(message: String): InvalidTaskDataException(message)
class InvalidTaskProjectIdException(message: String): InvalidTaskDataException(message)
class InvalidTaskStateNameException(message: String): InvalidTaskDataException(message)

open class InvalidUserDataException(message: String) : Exception(message)
class InvalidUserNameException(message: String) : InvalidUserDataException(message)
class InvalidPasswordException(message: String) : InvalidUserDataException(message)
class UserNotFoundException(message: String) : Exception(message)
class AccessDeniedException(message: String) : Exception(message)
class UserAlreadyExistsException(message: String) : Exception(message)
class UnknownException(message: String) : Exception(message)
class UnauthorizedAccessException(message: String) : Exception(message)
class InvalidProjectInfo(message: String) : Exception(message)
class TaskNotFoundException(message: String) : Exception(message)
class UserNotFoundBySelectedUserIdException(message: String) : Exception(message)