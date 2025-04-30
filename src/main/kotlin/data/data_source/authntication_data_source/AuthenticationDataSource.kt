package org.qudus.squad.data.data_source.authntication_data_source

import org.qudus.squad.model.MateUser
import org.qudus.squad.model.UserRole

interface AuthenticationDataSource {
    fun createMateUser(userRole: UserRole, user: MateUser)
    fun signIn(user: MateUser)
}