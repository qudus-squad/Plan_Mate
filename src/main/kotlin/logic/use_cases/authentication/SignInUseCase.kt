package logic.use_cases.authentication

import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.User

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userDataValidationUseCase: UserDataValidationUseCase
) {
    suspend fun signIn(username: String, password: String): User {
        userDataValidationUseCase.validateUserData(username, password)
        return authenticationRepository.signIn(username, password)
    }
}