package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

interface AuthenticationRepository {
    fun createNewUser(userRole: UserRole, user: User)
    fun signIn(user: User): Boolean
}