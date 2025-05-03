package org.qudus.squad.data.data_source.user_data_source

import org.qudus.squad.model.entity.User

interface UserDataSource {
    fun addUser(user: User)
    fun getUserById(userId: String): User?
    fun getAllUsers(): List<User>
}