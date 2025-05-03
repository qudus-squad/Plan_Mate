package logic.useCases.authentication

import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userValidator: UserDataValidator,
) {
    fun signIn(user: User): Boolean {
        return validateUserCredentials(user)
    }

    private fun validateUserCredentials(user: User): Boolean {
        if (!userValidator.validateUserData(user)) {
            throw InvalidUserDataException(INVALID_USER_NAME_OR_PASSWORD)
        }
        return authenticationRepository.signIn(user)
    }

    companion object {
        const val INVALID_USER_NAME_OR_PASSWORD = "Incorrect username or password"
    }
}