package logic.use_cases.authentication

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.data.data_source.authntication_data_source.CsvAuthenticationDataSource.Companion.USER_NOT_FOUND
import model.exceptions.InvalidPasswordException
import model.exceptions.InvalidUserNameException
import model.exceptions.UserNotFoundException
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class SignInUseCaseTest {
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var userDataValidationUseCase: UserDataValidationUseCase
    private lateinit var signInUseCase: SignInUseCase

    @BeforeEach
    fun setup() {
        authenticationRepository = mockk(relaxed = true)
        userDataValidationUseCase = UserDataValidationUseCase()
        signInUseCase = SignInUseCase(authenticationRepository, userDataValidationUseCase)
    }

    @Test
    fun `should throw InvalidUserNameException when username is empty`() {

        // Given
        val userName = ""
        val password = "12345678"

        // When && Then
        shouldThrow<InvalidUserNameException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username is blank`() {

        // Given
        val userName = ""
        val password = "12345678"

        // When && Then
        shouldThrow<InvalidUserNameException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username has unsupported symbols`() {

        // Given
        val userName = "Abdo*#$"
        val password = "12345678"

        // When && Then
        shouldThrow<InvalidUserNameException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password is empty`() {

        // Given
        val userName = "Abdo"
        val password = ""

        // When && Then
        shouldThrow<InvalidPasswordException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password is blank`() {

        // Given
        val userName = "Abdo"
        val password = "    "

        // When && Then
        shouldThrow<InvalidPasswordException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password less than 8 characters`() {

        // Given
        val userName = "Abdo"
        val password = "123"

        // When && Then
        shouldThrow<InvalidPasswordException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw UserNotFoundException when no credentials for entered data`() {

        // Given
        val userName = "Abdo"
        val password = "123456789"
        every {
            authenticationRepository.signIn(userName, password)
        } throws UserNotFoundException(USER_NOT_FOUND)

        // When & Then
        shouldThrow<UserNotFoundException> { signInUseCase.signIn(userName, password) }
    }

    @Test
    fun `should return true if user credentials are valid and authentication succeeds`() {

        // Given
        val username = "a.talaat_74"
        val password = "123456789"
        val validUser = User(username = "a.talaat_74", passwordHash = "123456789", role = UserRole.MATE)
        every { authenticationRepository.signIn(username, password) } returns validUser

        // When
        val user = signInUseCase.signIn(username, password)

        // Then
        user.username shouldBe "a.talaat_74"
    }
}