package org.qudus.squad.data.data_source.authntication_data_source

import org.qudus.squad.model.entity.User

interface AuthenticationDataSource {
    suspend fun signIn(username: String, password: String): User
}