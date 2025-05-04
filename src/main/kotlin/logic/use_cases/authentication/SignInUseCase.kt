package logic.use_cases.authentication

import org.qudus.squad.logic.exceptions.InvalidPasswordException
import org.qudus.squad.logic.exceptions.InvalidUserNameException
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.User

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userDataValidationUseCase: UserDataValidationUseCase
) {
    fun signIn(username: String, password: String): User {
        if (!userDataValidationUseCase.isValidUserName(username)) {
            throw InvalidUserNameException(INVALID_USER_NAME)
        }
        if (!userDataValidationUseCase.isValidPassword(password)) {
            throw InvalidPasswordException(INVALID_PASSWORD)
        }
        return authenticationRepository.signIn(username, password)
    }

    companion object {
        const val INVALID_USER_NAME =
            "Invalid username, username shouldn't be empty or contains symbols but \'.\' or \'_\' "
        const val INVALID_PASSWORD = "Password should be 8 characters or more"

    }

}