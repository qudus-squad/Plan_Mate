package org.qudus.squad.data.data_source.user_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
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
        if (userCollection.find(Filters.eq(ADD_USER_FILED_NAME, user.username)).firstOrNull() != null) {
            throw UserAlreadyExistsException()
        }
        userCollection.insertOne(user.toUserDto())
        return true
    }

    override suspend fun getUserById(userId: String): User {
        val dtoUser = userCollection.find(Filters.eq(USER_FILED_NAME, userId)).firstOrNull() ?: throw UserNotFoundException()
        return dtoUser.toUser()
    }

    override suspend fun getAllUsers(): List<User> {
        return userCollection.find().toList().map { it.toUser() }
    }

    override suspend fun deleteUser(userId: String) {
        val result = userCollection.deleteOne(Filters.eq(USER_FILED_NAME, userId))
        if (result.deletedCount == 0L) throw UserNotFoundException()
    }

    override suspend fun getUserByProjectId(userId: String): User {
        val projectId =
            mongoDatabase.getCollection<ProjectDto>(PROJECT_COLLECTION_NAME).find(Filters.eq(CREATE_USER_FILED_NAME , userId)).first()

        return mongoDatabase.getCollection<UserDto>(USER_COLLECTION_NAME).find(Filters.eq(PROJECT_FILED_NAME, projectId)).first().toUser()
    }

    private fun provideUserCollection(database: MongoDatabase): MongoCollection<UserDto> {
        return database.getCollection<UserDto>(PROVIDER_USER_COLLECTION_NAME).withDocumentClass(UserDto::class.java)
    }

    companion object {
        const val USER_FILED_NAME = "userId"
        const val ADD_USER_FILED_NAME = "username"
        const val CREATE_USER_FILED_NAME = "creatorUserId"
        const val PROJECT_FILED_NAME = "projectId"
        const val PROJECT_COLLECTION_NAME = "projects"
        const val USER_COLLECTION_NAME = "user"
        const val PROVIDER_USER_COLLECTION_NAME = "users"
    }

}