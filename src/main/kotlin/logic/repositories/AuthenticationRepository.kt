package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.User

interface AuthenticationRepository {
    suspend fun signIn(username: String, password: String): User
}