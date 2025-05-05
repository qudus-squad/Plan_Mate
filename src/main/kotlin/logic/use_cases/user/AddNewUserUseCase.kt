package logic.use_cases.user

import org.qudus.squad.logic.exceptions.AccessDeniedException
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class AddNewUserUseCase(
    private val userRepository: UserRepository,
    private val userValidator: UserDataValidationUseCase,
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
        userValidator.validateUserData(username, password)
        val user = User(username = username, passwordHash = mD5.generateHash(password), role = userRole)
        return userRepository.addNewUser(user)
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}