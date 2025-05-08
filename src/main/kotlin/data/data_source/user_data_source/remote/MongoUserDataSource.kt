package org.qudus.squad.data.data_source.user_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.exceptions.UserAlreadyExistsException
import logic.exceptions.UserNotFoundException
import org.qudus.squad.data.data_source.user_data_source.UserDataSource
import org.qudus.squad.model.entity.User

class MongoUserDataSource(
    private val mongoDatabase: MongoDatabase
) : UserDataSource {
    override suspend fun addUser(user: User): Boolean {
        isUserAlreadyExists(userName = user.username)
        mongoDatabase.getCollection<UserDto>("users").insertOne(user.toUserDto())
        return true
    }

    override suspend fun getUserById(userId: String): User {
        val dtoUser = mongoDatabase.getCollection<UserDto>("users").find(Filters.eq("userId", userId))
            .firstOrNull() ?: throw UserNotFoundException()
        return dtoUser.toUser()

    }

    override suspend fun getAllUsers(): List<User> {
        mongoDatabase.getCollection<UserDto>("users").find().toList().apply {
            return this.map { it.toUser() }
        }
    }

    override suspend fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    private suspend fun isUserAlreadyExists(userName: String) {
        if (mongoDatabase.getCollection<UserDto>("users").find(Filters.eq("username", userName))
                .firstOrNull() != null
        ) {
            throw UserAlreadyExistsException()
        }
    }
}