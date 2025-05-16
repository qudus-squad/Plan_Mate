package org.qudus.squad.data.data_source.user_data_source

import org.qudus.squad.model.entity.User

interface UserDataSource {
    suspend fun addUser(user: User): User
    suspend fun getUserById(userId: String): User
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(userId: String) : Boolean
    suspend fun getUserByProjectId(projectId: String) : User
}