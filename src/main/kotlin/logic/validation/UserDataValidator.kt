package org.qudus.squad.logic.validation

import org.qudus.squad.model.User

class UserDataValidator {

    fun validateUserData(user: User): Boolean {
        return !(user.username.isEmpty() ||
                user.passwordHash.isEmpty() ||
                user.username.all { !it.isLetterOrDigit() }
                )
    }

}