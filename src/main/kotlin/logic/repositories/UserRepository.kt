package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.User

interface UserRepository {
    suspend fun addNewUser(user: User): Boolean
    suspend fun getUserById(userId: String): User
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(userId: String)
}