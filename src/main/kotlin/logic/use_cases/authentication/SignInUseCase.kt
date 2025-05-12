package logic.use_cases.authentication

import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.TargetType
import org.qudus.squad.logic.model.User

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val userDataValidationUseCase: UserDataValidationUseCase,
    private val logRepository: LogRepository
) {
    suspend fun signIn(username: String, password: String): User {
        userDataValidationUseCase.validateUserData(username, password)
        val user = authenticationRepository.signIn(username, password)

        logRepository.addNewLog(LogEntry(
            userName = username,
            targetId = user.userId,
            targetType = TargetType.USER,
            action = "the user with id ${user.userId} signIn"
        ))
        return authenticationRepository.signIn(username, password)
    }
}