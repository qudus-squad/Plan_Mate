package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.User

interface AuthenticationRepository {
    fun signIn(username: String, password: String): User
}