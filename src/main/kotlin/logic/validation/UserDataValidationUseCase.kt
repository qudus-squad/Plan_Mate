package org.qudus.squad.logic.validation

import logic.exceptions.InvalidPasswordException
import logic.exceptions.InvalidUserNameException


class UserDataValidationUseCase {
    fun validateUserData(userName: String, password: String): Boolean {
        if (!isValidUserName(userName)){
            throw InvalidUserNameException(INVALID_PASSWORD)
        }
        if (!isValidPassword(password)){
            throw InvalidPasswordException(INVALID_USER_NAME)
        }
        return true
    }

    private fun isValidUserName(username: String): Boolean {
        val formattedUserName = username.trim()
        return formattedUserName.isNotEmpty() && username.all { ch ->
            ch.isLetterOrDigit() || ch == '.' || ch == '_'
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return !(password.isEmpty() || password.length < 8)
    }

    companion object {
        const val INVALID_USER_NAME =
            "Invalid username, username shouldn't be empty or contains symbols but \'.\' or \'_\' "
        const val INVALID_PASSWORD = "Password should be 8 characters or more"
    }

}