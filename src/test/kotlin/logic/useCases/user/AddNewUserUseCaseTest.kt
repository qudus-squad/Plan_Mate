package logic.useCases.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.useCases.user.AddNewUserUseCase
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.model.exceptions.AccessDeniedException
import org.qudus.squad.model.exceptions.InvalidUserDataException
import org.qudus.squad.model.exceptions.UserAlreadyExistException
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
    fun `should throw UserAlreadyExistException when the user already exists`() {
        // Given
        val currentUserRole = UserRole.ADMIN
        val username = "Abdo"
        val password = "12356"
        val userRole = UserRole.MATE

        val user = User(username = username, passwordHash = mD5.generateHash(password), role = userRole)

        every {
            userRepository.addNewUser(user)
        } throws UserAlreadyExistException("User Already Exist")

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

}