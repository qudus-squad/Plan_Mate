package logic.use_cases.authentication

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class SignInUseCaseTest {
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var userDataValidator: UserDataValidator
    private lateinit var signInUseCase: SignInUseCase

    @BeforeEach
    fun setup() {
        authenticationRepository = mockk(relaxed = true)
        userDataValidator = UserDataValidator()
        signInUseCase = SignInUseCase(authenticationRepository, userDataValidator)
    }

    @Test
    fun `should throw InvalidUserDataException when username is empty`() {

        // Given
        val userName = ""
        val password = "123456"

        // When && Then
        shouldThrow<InvalidUserDataException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidUserDataException when pass is empty`() {

        // Given
        val userName = "Abdo"
        val password = ""

        // When && Then
        shouldThrow<InvalidUserDataException> {
            signInUseCase.signIn(userName, password)
        }
    }

    @Test
    fun `should throw InvalidUserDataException when no credentials for entered data`() {

        // Given
        val userName = "Abdo"
        val password = "123456"
        every {
            authenticationRepository.signIn(userName, password)
        } throws InvalidUserDataException(INVALID_USER_DATA)

        // When & Then
        shouldThrow<InvalidUserDataException> { signInUseCase.signIn(userName, password) }


    }

    @Test
    fun `should return true if user credentials are valid and authentication succeeds`() {

        // Given
        val username = "Abdo"
        val password = "123456"
        val validUser = User(username = "Abdo", passwordHash = "123456", role = UserRole.MATE)
        every { authenticationRepository.signIn(username, password) } returns validUser

        // When
        val user = signInUseCase.signIn(username, password)

        // Then
        user.username shouldBe "Abdo"
    }

    companion object {
        const val INVALID_USER_DATA = "Invalid username or password"
    }
}