package logic.use_cases.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import logic.exceptions.AccessDeniedException
import logic.exceptions.InvalidPasswordException
import logic.exceptions.InvalidUserNameException
import logic.exceptions.UserAlreadyExistsException
import org.qudus.squad.logic.utils.DataHashing
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase.Companion.INVALID_USER_NAME
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import kotlin.test.Test

class AddNewUserUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userValidator: UserDataValidationUseCase
    private lateinit var   hashing: DataHashing
    private lateinit var addNewUserUseCase: AddNewUserUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk(relaxed = true)
        userValidator = UserDataValidationUseCase()
        hashing = mockk(relaxed = true)
        addNewUserUseCase = AddNewUserUseCase(userRepository, userValidator, hashing)
    }

    @Test
    fun `should return true when add a user with valid username and password`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = "123456789"
        val userRole = UserRole.MATE
        every { userRepository.addNewUser(any()) } returns true

        // When
        val result = addNewUserUseCase.addUser(
            currentUserRole = currentUserRole,
            username = username,
            password = password,
            userRole = userRole
        )

        // Then
        result shouldBe true
    }

    @Test
    fun `should throw AccessDeniedException when add user without admin role`() {
        // Given
        val currentUserRole = UserRole.MATE
        val username = "Abdo"
        val password = "12356789"
        val userRole = UserRole.MATE

        // When & Then
        shouldThrow<AccessDeniedException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }

    }

    @Test
    fun `should throw InvalidUserNameException when username empty`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = ""
        val password = "12356789"
        val userRole = UserRole.MATE

        // When & Then
        shouldThrow<InvalidUserNameException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username blank`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "    "
        val password = "12356789"
        val userRole = UserRole.MATE

        // When & Then
        shouldThrow<InvalidUserNameException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    @Test
    fun `should throw InvalidUserNameException when username has unsupported symbols`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo*@#"
        val password = "12356789"
        val userRole = UserRole.MATE
        val user = User(username = username, passwordHash = password, role = userRole)
        every { userRepository.addNewUser(user) } throws InvalidUserNameException(INVALID_USER_NAME)

        // When & Then
        shouldThrow<InvalidUserNameException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password is not empty`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = ""
        val userRole = UserRole.MATE

        // When & Then
        shouldThrow<InvalidPasswordException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password is not blank`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = "    "
        val userRole = UserRole.MATE

        // When & Then
        shouldThrow<InvalidPasswordException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    @Test
    fun `should throw InvalidPasswordException when password less than 8 characters`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = "123"
        val userRole = UserRole.MATE

        // When & Then
        shouldThrow<InvalidPasswordException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    @Test
    fun `should throw UserAlreadyExistException there the user data is already saved previously`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = "123456789"
        val userRole = UserRole.MATE
        every { userRepository.addNewUser(any()) } throws  UserAlreadyExistsException(USER_ALREADY_EXIST)

        // When & Then
        shouldThrow<UserAlreadyExistsException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    companion object {
        const val USER_ALREADY_EXIST = "User Already Exist"
    }
}