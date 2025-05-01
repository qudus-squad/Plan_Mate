package logic.useCases

import io.mockk.mockk
import logic.useCases.authentication.CreateNewUserUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.data.CredentialManager
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidator

class CreateNewUserUseCaseTest {
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var createNewMateUserUseCase: CreateNewUserUseCase
    private lateinit var userDataValidator: UserDataValidator
    private lateinit var credentialManager: CredentialManager

    @BeforeEach
    fun setup() {
        authenticationRepository = mockk(relaxed = true)
        userDataValidator = UserDataValidator()
        credentialManager = CredentialManager()
        createNewMateUserUseCase =
            CreateNewUserUseCase(authenticationRepository, userDataValidator)
    }

    @Test
    fun `should return true if all data are valid`() {
        // Given

        // When

        // Then

    }

    @Test
    fun `should throw InvalidDataUserException if username is empty`() {
        // Given

        // When && Then



    }

    @Test
    fun `should throw InvalidDataUserException if password is empty`() {
        // Given

        // When && Then
    }

    @Test
    fun `should throw InvalidDataUserException if the username is already exists`() {
        // Given

        // When && Then
    }

    @Test
    fun `should throw AccessDeniedException if the user role is not admin`() {
        // Given

        // When && Then


}
    }