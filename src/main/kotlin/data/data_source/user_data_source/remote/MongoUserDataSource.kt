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
import org.qudus.squad.data.data_source.provideCollection
import org.qudus.squad.data.data_source.task_state_data_source.remote.MongoTaskStateDataSource.Companion.COLLECTION_NAME
import org.qudus.squad.data.data_source.task_state_data_source.remote.TaskStateDto
import org.qudus.squad.data.data_source.user_data_source.*
import org.qudus.squad.model.entity.User

class MongoUserDataSource(
    private val mongoDatabase: MongoDatabase
) : UserDataSource {

    private val userCollection = provideCollection(mongoDatabase, PROVIDER_USER_COLLECTION_NAME, UserDto::class.java)

    override suspend fun addUser(user: User): Boolean {
        try {
            if (userCollection.find(Filters.eq(ADD_USER_FIELD_NAME, user.username)).firstOrNull() != null) {
                throw UserAlreadyExistsException()
            }
            userCollection.insertOne(user.toUserDto())
            return true
        } catch (e: Exception) {
            throw InvalidToAddUserException(FAILED_ADD_USER)
        }
    }

    override suspend fun getUserById(userId: String): User {
        return try {
            val dtoUser =
                userCollection.find(Filters.eq(USER_FIELD_NAME, userId)).firstOrNull() ?: throw UserNotFoundException()
            dtoUser.toUser()
        } catch (e: Exception) {
            throw InvalidToGetByIdUserException(FAILED_GET_USER_BY_ID)
        }
    }

    override suspend fun getAllUsers(): List<User> {
        return try {
            userCollection.find().toList().map { it.toUser() }
        } catch (e: Exception) {
            throw InvalidToGetAllUsersException(FAILED_GET_ALL_USERS)
        }
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return try {
            val result = userCollection.deleteOne(Filters.eq(USER_FIELD_NAME, userId))
            if (result.deletedCount == 0L) throw UserNotFoundException()
            true
        } catch (e: Exception) {
            throw InvalidToDeleteUserException(FAILED_DELETE_USER)
        }
    }


    override suspend fun getUserByProjectId(userId: String): User {
        val projectId = mongoDatabase.getCollection<ProjectDto>(PROJECT_COLLECTION_NAME)
            .find(Filters.eq(CREATE_USER_FIELD_NAME, userId)).first()

        return mongoDatabase.getCollection<UserDto>(USER_COLLECTION_NAME)
            .find(Filters.eq(PROJECT_FIELD_NAME, projectId)).first().toUser()
    }


    companion object {
        const val USER_FIELD_NAME = "userId"
        const val ADD_USER_FIELD_NAME = "username"
        const val CREATE_USER_FIELD_NAME = "creatorUserId"
        const val PROJECT_FIELD_NAME = "projectId"
        const val PROJECT_COLLECTION_NAME = "projects"
        const val USER_COLLECTION_NAME = "user"
        const val PROVIDER_USER_COLLECTION_NAME = "users"
    }
}