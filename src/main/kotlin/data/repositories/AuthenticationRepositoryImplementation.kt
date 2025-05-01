package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.authntication_data_source.AuthenticationDataSource
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class AuthenticationRepositoryImplementation(
    private val authenticationDataSource: AuthenticationDataSource,
) : AuthenticationRepository {

    override fun createNewUser(userRole: UserRole,user: User ) {
        authenticationDataSource.createNewUser(userRole, user)
    }

    override fun signIn(user: User):Boolean {
        return authenticationDataSource.signIn(user)
    }

}