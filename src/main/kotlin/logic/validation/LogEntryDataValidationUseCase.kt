package org.qudus.squad.logic.validation

import logic.exceptions.InvalidLogActionException
import logic.exceptions.InvalidLogTargetIdException
import logic.exceptions.InvalidLogUserNameException
import org.qudus.squad.logic.model.LogEntry

class LogEntryDataValidationUseCase {

    fun validateLogEntry(log: LogEntry) {
        if (!isValidUserName(log.userName)) {
            throw InvalidLogUserNameException(INVALID_USER_NAME)
        }
        if (!isValidTargetId(log.targetId)) {
            throw InvalidLogTargetIdException(INVALID_TARGET_ID)
        }
        if (!isValidAction(log.action)) {
            throw InvalidLogActionException(INVALID_ACTION)
        }
    }

    private fun isValidUserName(userName: String): Boolean {
        return userName.isNotBlank()
    }

    private fun isValidTargetId(targetId: String): Boolean {
        return targetId.isNotBlank()
    }

    private fun isValidAction(action: String): Boolean {
        return action.isNotBlank() && action.isNotEmpty()
    }

    companion object {
        const val INVALID_USER_NAME = "Log userName cannot be blank"
        const val INVALID_TARGET_ID = "Log targetId cannot be blank"
        const val INVALID_ACTION = "Log action cannot be blank or empty"
    }
}