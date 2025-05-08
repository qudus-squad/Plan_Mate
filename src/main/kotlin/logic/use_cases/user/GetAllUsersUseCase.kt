package logic.use_cases.user

import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.model.entity.User

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }
}