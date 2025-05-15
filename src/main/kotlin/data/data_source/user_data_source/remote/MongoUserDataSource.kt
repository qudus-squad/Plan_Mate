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
        if (userCollection.find(Filters.eq(ADD_USER_FIELD_NAME, user.username)).firstOrNull() != null) {
            throw UserAlreadyExistsException()
        }
        val result = userCollection.insertOne(user.toUserDto()).wasAcknowledged()
        if (!result) throw InvalidToAddUserException()

        return true
    }

    override suspend fun getUserById(userId: String): User {
        val dtoUser =
            userCollection.find(Filters.eq(USER_FIELD_NAME, userId)).firstOrNull() ?: throw InvalidToGetByIdUserException()
        return dtoUser.toUser()
    }

    override suspend fun getAllUsers(): List<User> {

        val users = userCollection.find().toList().map { userDto -> userDto.toUser() }
        if (users.isEmpty()) throw InvalidToGetAllUsersException()
        return users
    }

    override suspend fun deleteUser(userId: String): Boolean {
        val result = userCollection.deleteOne(Filters.eq(USER_FIELD_NAME, userId)).wasAcknowledged()
        if (!result) throw InvalidToDeleteUserException()
        return true
    }


    override suspend fun getUserByProjectId(userId: String): User {
        val projectId = mongoDatabase.getCollection<ProjectDto>(PROJECT_COLLECTION_NAME)
            .find(Filters.eq(CREATE_USER_FIELD_NAME, userId)).firstOrNull()?.id ?: throw UserNotFoundException()

        val userDto =
            mongoDatabase.getCollection<UserDto>(USER_COLLECTION_NAME).find(Filters.eq(PROJECT_FIELD_NAME, projectId))
                .firstOrNull() ?: throw InvalidToGetUserByProjectIdException()
        return userDto.toUser()
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