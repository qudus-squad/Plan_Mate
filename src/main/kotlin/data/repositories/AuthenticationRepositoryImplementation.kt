package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.authntication_data_source.AuthenticationDataSource
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.model.entity.User

class AuthenticationRepositoryImplementation(
    private val authenticationDataSource: AuthenticationDataSource,
) : AuthenticationRepository {


    override suspend fun signIn(username: String, password: String): User {
        return authenticationDataSource.signIn(username, password)
    }

}