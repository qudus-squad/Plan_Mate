package logic.useCases.authentication

import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.model.exceptions.AccessDeniedException
import org.qudus.squad.model.exceptions.InvalidUserDataException
import org.qudus.squad.model.exceptions.UnknownException

class CreateNewUserUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userDataValidator: UserDataValidator,
) {

    fun createNewMateUser(userRole: UserRole, user: User): Boolean {
        if (userRole != UserRole.ADMIN) {
            throw AccessDeniedException(ACCESS_DENIED)
        }

        if (!userDataValidator.validateUserData(user)) {
            throw InvalidUserDataException(INVALID_USER_NAME_OR_PASSWORD)
        }
        try {
            authenticationRepository.createNewUser(userRole, user)
            return true
        } catch (e: Exception) {
            throw UnknownException(e.message.toString())
        }
    }

    companion object {
        const val INVALID_USER_NAME_OR_PASSWORD = "Incorrect username or password"
        const val ACCESS_DENIED = "Access Denied"
    }
}