package logic.use_cases.user

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exceptions.InvalidUserIdException
import logic.exceptions.InvalidUserNameException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.use_cases.user.DeleteUserUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.model.User
import org.qudus.squad.logic.model.UserRole

class DeleteUserUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var logRepository: LogRepository
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @BeforeEach
    fun setup(){
        userRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        deleteUserUseCase = DeleteUserUseCase(userRepository,logRepository, UserDataValidationUseCase())
    }

    @Test
    fun `deleteUser should delegate user deletion to user repository`(){
        runTest {
            // Given
            val user1 = User(
                userId = "125" ,
                username = "hazem",
                passwordHash = "" ,
                role = UserRole.MATE
            )
            // when
            deleteUserUseCase.deleteUser(user1,user1.userId)
            // Then
            coVerify(exactly = 1) { userRepository.deleteUser(user1.userId) }
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username has empty value`(){
        runTest {
            // Given
            val user2 = User(
                userId = "125" ,
                username = "",
                passwordHash = "" ,
                role = UserRole.MATE
            )
            // Then & when
            shouldThrow<InvalidUserNameException> {
                deleteUserUseCase.deleteUser(user2,user2.userId)
            }
        }
    }

    @Test
    fun `should throw InvalidUserIdException when userId has empty value`(){
        runTest {
            // Given
            val user3 = User(
                userId = "" ,
                username = "hazem",
                passwordHash = "" ,
                role = UserRole.MATE
            )
            // Then & when
            shouldThrow<InvalidUserIdException> {
                deleteUserUseCase.deleteUser(user3,user3.userId)
            }
        }
    }
}