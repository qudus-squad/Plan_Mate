package org.qudus.squad.logic.validation

import org.qudus.squad.model.MateUser

class UserDataValidator {

    fun validateUserData(user: MateUser):Boolean{
        return !(user.username.isEmpty()||user.passwordHash.isEmpty()||user.passwordHash.isBlank())
    }

}