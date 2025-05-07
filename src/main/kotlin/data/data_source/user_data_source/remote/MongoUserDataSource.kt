package org.qudus.squad.data.data_source.user_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import logic.exceptions.UserAlreadyExistsException
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
        isUserIdAlreadyExists(userId)
        return userCollection.find(Filters.eq("userId", userId))
            .firstOrNull()!!
            .toUser()
    }

    override fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    private suspend fun isUserAlreadyExists(userName: String) {
        if (userCollection.find(Filters.eq("username", userName)).firstOrNull() != null){
            throw UserAlreadyExistsException()
        }
    }
    private suspend fun isUserIdAlreadyExists(userId: String) {
        if (userCollection.find(Filters.eq("userId", userId)).firstOrNull() == null) {
            println("user with selected id does not exist")
        }
    }
}