package org.qudus.squad.data.data_source.authntication_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import logic.exceptions.UserNotFoundException
import org.qudus.squad.data.data_source.authntication_data_source.AuthenticationDataSource
import org.qudus.squad.data.data_source.user_data_source.remote.UserDto
import org.qudus.squad.data.data_source.user_data_source.remote.toUser
import org.qudus.squad.ui.utils.DataHashing
import org.qudus.squad.logic.model.User

class MongoAuthenticationDataSource(
    private val mongoDatabase: MongoDatabase,
    private val hashing: DataHashing

) : AuthenticationDataSource {
    override suspend fun signIn(username: String, password: String): User {
        val userDto = mongoDatabase.getCollection<UserDto>(COLLECTION_NAME).find(
            Filters.and(
                Filters.eq(USERNAME_FIELD, username),
                Filters.eq(
                    PASSWORD_FIELD,
                    hashing.generateHash(password)
                )
            )
        ).firstOrNull() ?: throw UserNotFoundException()
        return userDto.toUser()
    }

    companion object {
        const val USERNAME_FIELD = "username"
        const val PASSWORD_FIELD = "passwordHash"
        const val COLLECTION_NAME = "users"
    }
}