package org.qudus.squad.data.repositories

import org.qudus.squad.data.data_source.user_data_source.UserDataSource
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.model.entity.User

class UserRepositoryImplementation(
    private val userDataSource: UserDataSource
) : UserRepository {
    override fun addNewUser(user: User): Boolean {
        return userDataSource.addUser(user)
    }

    override fun getUserById(userId: String): User? {
        return userDataSource.getUserById(userId)
    }

    override fun getAllUsers(): List<User> {
        return userDataSource.getAllUsers()
    }
}