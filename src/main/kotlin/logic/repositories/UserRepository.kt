package org.qudus.squad.logic.repositories

import org.qudus.squad.model.entity.User

interface UserRepository {
    fun addNewUser(user: User): Boolean
    fun getUserById(userId: String): User
    fun getAllUsers(): List<User>
}