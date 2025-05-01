package logic.usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.usecases.authentication.SignInUseCase
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.MateUser
import org.qudus.squad.model.exceptions.InvalidUserDataException

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
    fun `should throw InvalidUserDataException if user data is invalid`() {

        // Given
        val invalidUser = MateUser(username = "", passwordHash = "")

        // When && Then
        shouldThrow<InvalidUserDataException> {
            signInUseCase.signIn(invalidUser)
        }
    }

    @Test
    fun `should return false if user data are valid but authentication fails`() {

        // Given
        val validUser = MateUser(username = "validUser", passwordHash = "validPasswordHash")
        every { authenticationRepository.signIn(validUser) } returns false

        // When
        val result = signInUseCase.signIn(validUser)

        // Then
        result shouldBe false
    }

    @Test
    fun `should return true if user credentials are valid and authentication succeeds`() {

        // Given
        val validUser = MateUser(username = "validUser", passwordHash = "validPasswordHash")
        every { authenticationRepository.signIn(validUser) } returns true

        // When
        val result = signInUseCase.signIn(validUser)

        // Then
        result shouldBe true
    }
}