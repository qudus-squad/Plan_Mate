package org.qudus.squad.logic.useCases.user

import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.model.entity.User

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    fun getAllUSers(): List<User> {
        return userRepository.getAllUsers()
    }
}