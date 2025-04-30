package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.authntication_data_source.AuthenticationDataSource
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.model.MateUser
import org.qudus.squad.model.UserRole

class AuthenticationRepositoryImplementation(
    private val authenticationDataSource: AuthenticationDataSource,
) : AuthenticationRepository {

    override fun createMateUser(userRole: UserRole, user: MateUser) {
        authenticationDataSource.createMateUser(userRole, user)
    }

    override fun signIn(user: MateUser) {
    }

}