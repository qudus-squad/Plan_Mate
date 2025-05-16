package logic.use_cases.user

import org.qudus.squad.ui.utils.DataHashing
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.validation.UserRoleValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class AddNewUserUseCase(
    private val userRepository: UserRepository,
    private val userValidator: UserDataValidationUseCase,
    private val hashing: DataHashing,
    private val userRoleValidationUseCase: UserRoleValidationUseCase
) {
    suspend fun addUser(
        currentUserRole: UserRole, username: String, password: String, userRole: UserRole
    ): User {
        userRoleValidationUseCase.checkUserRoleIsAdmin(currentUserRole)
        userValidator.validateUserData(username, password)
        val user = User(username = username, passwordHash = hashing.generateHash(password), role = userRole)
        userRepository.addUser(user)

        return user
    }
}