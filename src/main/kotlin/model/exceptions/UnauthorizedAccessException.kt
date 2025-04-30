package org.qudus.squad.model.exceptions

class UnauthorizedAccessException(
    message: String = NOT_AUTHORIZED_EXCEPTION_MESSAGE,
) : Exception(message) {

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}