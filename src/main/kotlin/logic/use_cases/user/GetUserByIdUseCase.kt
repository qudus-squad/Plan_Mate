package org.qudus.squad.logic.use_cases.user

import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.model.User

class GetUserByIdUseCase(
    private val userRepository: UserRepository, private val userDataValidationUseCase: UserDataValidationUseCase
) {
    suspend fun getUserById(userid: String): User {
        userDataValidationUseCase.validateUserByIdValues(userid)
        return userRepository.getUserById(userid)
    }
}