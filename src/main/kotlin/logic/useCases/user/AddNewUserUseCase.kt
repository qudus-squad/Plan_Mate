package org.qudus.squad.logic.useCases.user

import org.qudus.squad.logic.exceptions.AccessDeniedException
import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.exceptions.UserAlreadyExistException
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class AddNewUserUseCase(
    private val userRepository: UserRepository,
    private val userValidator: UserDataValidator,
    private val mD5: EncryptionByUsingMD5
) {
    fun addUser(
        currentUserRole: UserRole,
        username: String,
        password: String,
        userRole: UserRole
    ): Boolean {
        if (currentUserRole != UserRole.ADMIN) {
            throw AccessDeniedException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
        }
        val user = User(username = username, passwordHash = mD5.generateHash(password), role = userRole)
        if (!userValidator.validateUserData(user)) {
            throw InvalidUserDataException(INVALID_INPUT)
        } else {
            return try {
                userRepository.addNewUser(user)
                true
            } catch (e: UserAlreadyExistException) {
                throw e
            }
        }
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
        const val INVALID_INPUT = "Invalid username or password"

    }
}