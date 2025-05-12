package org.qudus.squad.data.data_source.user_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.exceptions.ProjectNotFoundException
import logic.exceptions.UserAlreadyExistsException
import logic.exceptions.UserNotFoundException
import org.qudus.squad.data.data_source.project_data_source.remote.ProjectDto
import org.qudus.squad.data.data_source.user_data_source.UserDataSource
import org.qudus.squad.model.entity.User

class MongoUserDataSource(
    private val mongoDatabase: MongoDatabase
) : UserDataSource {

    private val userCollection: MongoCollection<UserDto> = provideUserCollection(mongoDatabase)

    override suspend fun addUser(user: User): Boolean {
        if (userCollection.find(Filters.eq("username", user.username)).firstOrNull() != null) {
            throw UserAlreadyExistsException()
        }
        userCollection.insertOne(user.toUserDto())
        return true
    }

    override suspend fun getUserById(userId: String): User {
        val dtoUser = userCollection.find(Filters.eq("userId", userId)).firstOrNull() ?: throw UserNotFoundException()
        return dtoUser.toUser()
    }

    override suspend fun getAllUsers(): List<User> {
        return userCollection.find().toList().map { it.toUser() }
    }

    override suspend fun deleteUser(userId: String) {
        val result = userCollection.deleteOne(Filters.eq("userId", userId))
        if (result.deletedCount == 0L) throw UserNotFoundException()
    }

    override suspend fun getUserByProjectId(userId: String): User {
        val projectId =
            mongoDatabase.getCollection<ProjectDto>("projects").find(Filters.eq("creatorUserId", userId)).first()

        return mongoDatabase.getCollection<UserDto>("user").find(Filters.eq("projectId", projectId)).first().toUser()
    }

    private fun provideUserCollection(database: MongoDatabase): MongoCollection<UserDto> {
        return database.getCollection<UserDto>("users").withDocumentClass(UserDto::class.java)
    }
}