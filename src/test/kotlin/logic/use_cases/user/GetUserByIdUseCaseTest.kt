package logic.use_cases.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exceptions.UserNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.use_cases.user.GetUserByIdUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class GetUserByIdUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUserByIdUseCase: GetUserByIdUseCase
    private lateinit var userDataValidationUseCase: UserDataValidationUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk(relaxed = true)
        userDataValidationUseCase = UserDataValidationUseCase()
        getUserByIdUseCase = GetUserByIdUseCase(userRepository, userDataValidationUseCase)
    }

    @Test
    fun `should return user by user id when user is found`() {
        runTest {
            // Given
            val user = User(
                username = "Farah",
                passwordHash = "fara-heba-777",
                userId = "user_1",
                role = UserRole.ADMIN
            )
            coEvery { userRepository.getUserById("user_1") } returns user

            // When
            val result = getUserByIdUseCase.getUserById("user_1")

            // Then
            result shouldBe user
        }
    }

    @Test
    fun `should throw UserNotFoundException when there are no users with selected id`() {
        runTest {
            // Given
            coEvery { userRepository.getUserById("user_999") } throws UserNotFoundException()

            // When & Then
            shouldThrow<UserNotFoundException> {
                getUserByIdUseCase.getUserById("user_999")
            }
        }
    }
}