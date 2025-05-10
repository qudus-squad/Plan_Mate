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
            val userName = "hazem"
            val userId = "125"
            // when
            deleteUserUseCase.deleteUser(userName,userId)
            // Then
            coVerify(exactly = 1) { userRepository.deleteUser(userId) }
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username has empty value`(){
        runTest {
            // Given
            val userName = " "
            val userId = "12563"
            // Then & when
            shouldThrow<InvalidUserNameException> {
                deleteUserUseCase.deleteUser(userName,userId)
            }
        }
    }

    @Test
    fun `should throw InvalidUserIdException when userId has empty value`(){
        runTest {
            // Given
            val userName = "ahmed"
            val userId = ""
            // Then & when
            shouldThrow<InvalidUserIdException> {
                deleteUserUseCase.deleteUser(userName,userId)
            }
        }
    }
}