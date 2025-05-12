package logic.use_cases.authentication

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exceptions.InvalidPasswordException
import logic.exceptions.InvalidUserNameException
import logic.exceptions.UserNotFoundException
import org.qudus.squad.data.repositories.LogRepositoryImplementation
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class SignInUseCaseTest {
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var userDataValidationUseCase: UserDataValidationUseCase
    private lateinit var signInUseCase: SignInUseCase
    private lateinit var lotRepository: LogRepository

    @BeforeEach
    fun setup() {
        authenticationRepository = mockk(relaxed = true)
        userDataValidationUseCase = UserDataValidationUseCase()
        lotRepository = mockk(relaxed = true)
        signInUseCase = SignInUseCase(authenticationRepository, userDataValidationUseCase,lotRepository)
    }

    @Test
    fun `should throw InvalidUserNameException when username is empty`() = runTest {

        // Given
        val userName = ""
        val password = "12345678"

        // When && Then
        shouldThrow<InvalidUserNameException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username is blank`() = runTest {

        // Given
        val userName = ""
        val password = "12345678"

        // When && Then
        shouldThrow<InvalidUserNameException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username has unsupported symbols`() = runTest {

        // Given
        val userName = "Abdo*#$"
        val password = "12345678"

        // When && Then
        shouldThrow<InvalidUserNameException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password is empty`() = runTest {

        // Given
        val userName = "Abdo"
        val password = ""

        // When && Then
        shouldThrow<InvalidPasswordException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password is blank`() = runTest {

        // Given
        val userName = "Abdo"
        val password = "    "

        // When && Then
        shouldThrow<InvalidPasswordException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password less than 8 characters`() = runTest {

        // Given
        val userName = "Abdo"
        val password = "123"

        // When && Then
        shouldThrow<InvalidPasswordException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw UserNotFoundException when no credentials for entered data`() = runTest {

        // Given
        val userName = "Abdo"
        val password = "123456789"
        coEvery {
            authenticationRepository.signIn(userName, password)
        } throws UserNotFoundException()

        // When & Then
        shouldThrow<UserNotFoundException> { signInUseCase.signIn(userName, password) }
    }

    @Test
    fun `should return true if user credentials are valid and authentication succeeds`() = runTest {

        // Given
        val username = "a.talaat_74"
        val password = "123456789"
        val validUser = User(username = "a.talaat_74", passwordHash = "123456789", role = UserRole.MATE)
        coEvery { authenticationRepository.signIn(username, password) } returns validUser

        // When
        val user = signInUseCase.signIn(username, password)

        // Then
        user.username shouldBe "a.talaat_74"
    }
}