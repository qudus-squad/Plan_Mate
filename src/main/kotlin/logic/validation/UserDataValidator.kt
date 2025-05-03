package org.qudus.squad.logic.validation


class UserDataValidator {

    fun validateUserData(username: String, password: String): Boolean {
        return !(username.isEmpty() ||
                password.isEmpty() ||
                username.all { !it.isLetterOrDigit() }
                )
    }
}