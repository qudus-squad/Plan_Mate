package org.qudus.squad.data.data_source.authntication_data_source

import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

interface AuthenticationDataSource {
    fun createNewUser(userRole: UserRole, user: User)
    fun signIn(user: User): Boolean
}