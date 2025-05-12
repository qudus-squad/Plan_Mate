package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.user_data_source.UserDataSource
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.model.User

class UserRepositoryImplementation(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun addNewUser(user: User): Boolean {
        return userDataSource.addUser(user)
    }

    override suspend fun getUserById(userId: String): User {
        return userDataSource.getUserById(userId)
    }

    override suspend fun getAllUsers(): List<User> {
        return userDataSource.getAllUsers()
    }
    override suspend fun deleteUser(userId: String) {
        return userDataSource.deleteUser(userId)
    }

    override suspend fun getUserByProjectId(projectId: String): User {
        return userDataSource.getUserByProjectId(projectId)
    }
}