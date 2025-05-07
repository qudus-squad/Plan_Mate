package org.qudus.squad.data.data_source.user_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.exceptions.UserAlreadyExistsException
import logic.exceptions.UserNotFoundException
import org.qudus.squad.data.data_source.user_data_source.UserDataSource
import org.qudus.squad.model.entity.User

class MongoUserDataSource(
    private val userCollection: MongoCollection<UserDto>
) : UserDataSource {
    override suspend fun addUser(user: User): Boolean {
        isUserAlreadyExists(userName = user.username)
        userCollection.insertOne(user.toUserDto())
        return true
    }

    override suspend fun getUserById(userId: String): User {
        val dtoUser = userCollection.find(Filters.eq("userId", userId)).firstOrNull()
            ?: throw UserNotFoundException()
        return dtoUser.toUser()
    }

    override suspend fun getAllUsers(): List<User> {
        userCollection.find().toList().apply {
            return this.map { it.toUser() }
        }
    }

    private suspend fun isUserAlreadyExists(userName: String) {
        if (userCollection.find(Filters.eq("username", userName)).firstOrNull() != null) {
            throw UserAlreadyExistsException()
        }
    }
}