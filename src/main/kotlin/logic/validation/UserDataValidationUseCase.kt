package org.qudus.squad.logic.validation


class UserDataValidationUseCase {

    fun isValidUserName(username: String): Boolean {
        val formattedUserName = username.trim()
        return formattedUserName.isNotEmpty() && username.all { ch ->
            ch.isLetterOrDigit() || ch == '.' || ch == '_'
        }
    }

    fun isValidPassword(password: String): Boolean {
        return !(password.isEmpty() || password.length < 8)
    }

}