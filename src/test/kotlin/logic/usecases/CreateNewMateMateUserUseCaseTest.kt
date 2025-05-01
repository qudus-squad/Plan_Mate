package logic.usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.data.CredentialManager
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.usecases.mate.CreateNewMateUserUseCase
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.MateUser
import org.qudus.squad.model.exceptions.AccessDeniedException
import org.qudus.squad.model.exceptions.InvalidUserDataException

class CreateNewMateMateUserUseCaseTest {
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var createNewMateUserUseCase: CreateNewMateUserUseCase
    private lateinit var userDataValidator: UserDataValidator
    private lateinit var credentialManager: CredentialManager

    @BeforeEach
    fun setup() {
        authenticationRepository = mockk(relaxed = true)
        userDataValidator = UserDataValidator()
        credentialManager = CredentialManager()
        createNewMateUserUseCase =
            CreateNewMateUserUseCase(authenticationRepository, userDataValidator)
    }

    @Test
    fun `should return true if all data are valid`() {
        // Given
        val unRealAdmin = AdminUser("Admin", "123456")
        val mateUser = MateUser("Abdo", "123456")

        // When
        val result = createNewMateUserUseCase.createNewMateUser(unRealAdmin.role, mateUser)

        // Then
        result shouldBe true
    }

    @Test
    fun `should throw InvalidDataUserException if username is empty`() {
        // Given
        val unRealAdmin = AdminUser("Admin", "123456")
        val mateUser = MateUser("", "123456")

        // When && Then
        shouldThrow<InvalidUserDataException> {
            createNewMateUserUseCase.createNewMateUser(unRealAdmin.role, mateUser)
        }


    }

    @Test
    fun `should throw InvalidDataUserException if password is empty`() {
        // Given
        val unRealAdmin = AdminUser("Admin", "123456")
        val mateUser = MateUser("abdo", "")

        // When && Then
        shouldThrow<InvalidUserDataException> {
            createNewMateUserUseCase.createNewMateUser(unRealAdmin.role, mateUser)
        }
    }

    @Test
    fun `should throw InvalidDataUserException if the username is already exists`() {
        // Given
        val unRealAdmin = AdminUser("Admin", "123456")
        val mateUser = MateUser("", "123456")

        // When && Then
        shouldThrow<InvalidUserDataException> {
            createNewMateUserUseCase.createNewMateUser(unRealAdmin.role, mateUser)
        }
    }

    @Test
    fun `should throw AccessDeniedException if the user role is not admin`() {
        // Given
        val unRealAdmin = MateUser("Admin", ("123456"))
        val mateUser = MateUser("Abdo", "123456")

        // When && Then
        shouldThrow<AccessDeniedException> {
            createNewMateUserUseCase.createNewMateUser(unRealAdmin.role, mateUser)
        }
    }

}