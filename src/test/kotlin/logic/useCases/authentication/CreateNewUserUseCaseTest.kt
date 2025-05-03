package logic.useCases.authentication

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.data.CredentialManager
import org.qudus.squad.data.CredentialManager.Companion.USER_ALREADY_EXIST
import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.exceptions.UserAlreadyExistException
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

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
        val unRealAdmin = User(username = "Admin", passwordHash = "123456", role = UserRole.ADMIN)
        val mateUser = User(username = "Abdo", passwordHash = "123456", role = UserRole.MATE)

        // When
        val result = createNewMateUserUseCase.createNewMateUser(unRealAdmin.role, mateUser)

        // Then
        result shouldBe true
    }

    @Test
    fun `should throw InvalidDataUserException if username is empty`() {
        // Given
        val admin = User(username = "Admin", passwordHash = "123456", role = UserRole.ADMIN)
        val mateUser = User(username = "", passwordHash = "123456", role = UserRole.MATE)

        // When && Then
        shouldThrow<InvalidUserDataException> {
            createNewMateUserUseCase.createNewMateUser(admin.role, mateUser)
        }


    }

    @Test
    fun `should throw InvalidDataUserException if password is empty`() {
        // Given
        val admin = User(username = "Admin", passwordHash = "123456", role = UserRole.ADMIN)
        val mateUser = User(username = "abdo", passwordHash = "", role = UserRole.MATE)

        // When && Then
        shouldThrow<InvalidUserDataException> {
            createNewMateUserUseCase.createNewMateUser(admin.role, mateUser)
        }
    }

    @Test
    fun `should throw UserAlreadyExistException if the username is already exists`() {
        // Given
        val admin = User("Admin", "123456", role = UserRole.ADMIN)
        val mateUser = User(username = "Admin", passwordHash = "123456", role = UserRole.MATE)

        every {
            createNewMateUserUseCase.createNewMateUser(admin.role, mateUser)
        } throws UserAlreadyExistException(
            USER_ALREADY_EXIST
        )

        // When && Then
        shouldThrow<UserAlreadyExistException> {
            createNewMateUserUseCase.createNewMateUser(admin.role, mateUser)
        }
    }

    @Test
    fun `should throw AccessDeniedException if the user role is not admin`() {
        // Given
        val admin = User(username = "Admin", passwordHash = "123456", role = UserRole.MATE)
        val mateUser = User(username = "Abdo", passwordHash = "123456", role = UserRole.MATE)

        // When && Then
        shouldThrow<AccessDeniedException> {
            createNewMateUserUseCase.createNewMateUser(admin.role, mateUser)
        }
    }

}