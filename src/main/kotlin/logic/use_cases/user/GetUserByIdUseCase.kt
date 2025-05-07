package org.qudus.squad.logic.use_cases.user

import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.model.entity.User

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend fun getUserById(userid: String): User {
        return userRepository.getUserById(userid)
    }
}