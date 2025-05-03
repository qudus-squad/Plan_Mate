package logic.useCases.user

import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.useCases.user.GetAllUsersUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import kotlin.test.Test


class GetAllUsersUseCaseTest {
    private lateinit var userRepository: UserRepository
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @BeforeEach
    fun setup() {
        userRepository = mockk(relaxed = true)
        getAllUsersUseCase = GetAllUsersUseCase(userRepository)
    }

    @Test
    fun `should return list of user when there are users`() {
        // Given
        val usersList = listOf(
            User(username = "Ahmed", passwordHash = "lsvnsdvs656s5dv", role = UserRole.MATE),
            User(username = "Mohamed", passwordHash = "afknskvas", role = UserRole.MATE),
            User(username = "Abdo", passwordHash = "adjfiojfief", role = UserRole.MATE),
        )
        every { userRepository.getAllUsers() } returns usersList

        //when
        val result = getAllUsersUseCase.getAllUSers()

        // Then
        result.map { it.username }.shouldContainExactly(
            "Ahmed",
            "Mohamed",
            "Abdo"
        )
    }

    @Test
    fun `should return empty list when there are no users `() {
        // Given
        val usersList = emptyList<User>()
        every { userRepository.getAllUsers() } returns usersList

        //when
        val result = getAllUsersUseCase.getAllUSers()

        // Then
        result.map { it.username }.shouldContainExactly()
    }
}