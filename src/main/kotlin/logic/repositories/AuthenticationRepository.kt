package org.qudus.squad.logic.repositories

import org.qudus.squad.model.MateUser
import org.qudus.squad.model.UserRole

interface AuthenticationRepository {
    fun createMateUser(userRole: UserRole, user: MateUser)
    fun signIn(user: MateUser)
}