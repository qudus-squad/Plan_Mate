package logic.use_cases.authentication

import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userDataValidator: UserDataValidator
) {
    fun signIn(username: String, password: String): User {
        if (!userDataValidator.validateUserData(username, password)) {
            throw InvalidUserDataException(INVALID_USER_DATA)
        }
        return try {
            authenticationRepository.signIn(username, password)
        } catch (e: InvalidUserDataException) {
            throw e
        }

    }

    companion object {
        const val INVALID_USER_DATA = "Invalid username or password"

    }

}