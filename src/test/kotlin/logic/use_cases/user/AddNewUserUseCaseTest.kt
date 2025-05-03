package logic.use_cases.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.exceptions.AccessDeniedException
import org.qudus.squad.logic.exceptions.UserAlreadyExistException
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import kotlin.test.Test

class AddNewUserUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userValidator: UserDataValidator
    private lateinit var mD5: EncryptionByUsingMD5
    private lateinit var addNewUserUseCase: AddNewUserUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk(relaxed = true)
        userValidator = UserDataValidator()
        mD5 = EncryptionByUsingMD5()
        addNewUserUseCase = AddNewUserUseCase(userRepository, userValidator, mD5)
    }

    @Test
    fun `should successfully add a valid user`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = "12356"
        val userRole = UserRole.MATE
        val user = User(username = username, passwordHash = password, role = userRole)
        every { userRepository.addNewUser(user) } returns Unit

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
        val password = "12356"
        val userRole = UserRole.MATE
        val user = User(username = username, passwordHash = password, role = userRole)

        every { userRepository.addNewUser(user) } throws AccessDeniedException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)

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
    fun `should throw InvalidUserDataException when username is not valid`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = ""
        val password = "12356"
        val userRole = UserRole.MATE
        val user = User(username = username, passwordHash = password, role = userRole)
        every { userRepository.addNewUser(user) } throws InvalidUserDataException(INVALID_INPUT)

        // When & Then
        shouldThrow<InvalidUserDataException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }

    }

    @Test
    fun `should throw InvalidUserDataException when password is not valid`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = ""
        val userRole = UserRole.MATE

        // When & Then
        shouldThrow<InvalidUserDataException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    @Test
    fun `should throw UserAlreadyExistException when password is not valid`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = "123456"
        val userRole = UserRole.MATE
        val user = User(username = username, passwordHash = password, role = userRole)

        every { userRepository.addNewUser(user) } throws  UserAlreadyExistException(USER_ALREADY_EXIST)

        // When & Then
        shouldThrow<UserAlreadyExistException> {
            addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = username,
                password = password,
                userRole = userRole
            )
        }
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
        const val INVALID_INPUT = "Invalid username or password"
        const val USER_ALREADY_EXIST = "User Already Exist"

    }
}