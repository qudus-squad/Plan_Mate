package org.qudus.squad.logic.repositories

import org.qudus.squad.logic.model.User

interface AuthenticationRepository {
    suspend fun signIn(username: String, password: String): User
}