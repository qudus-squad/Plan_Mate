package logic.usecases.state

import fakes.FakeStateRepository
import org.qudus.squad.logic.usecases.state.CreateStateUseCase
import org.junit.jupiter.api.Assertions.*
import org.qudus.squad.model.AdminUser
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.result.shouldBeSuccess
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateStateUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var createStateUseCase: CreateStateUseCase
    private val projectId = "P001"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        createStateUseCase = CreateStateUseCase(stateRepository)
    }

    @Test
    fun `should allow admin to create new state`() {
        // Given
        val admin = AdminUser(username = "admin", passwordHash = "hashed")

        // When
        val result = createStateUseCase.createState(admin, projectId, "In Progress")

        // Then
        result.shouldBeSuccess()
        val states = stateRepository.getAllStatesForProject(projectId)
        states.shouldHaveSize(1)
        states.map { it.name } shouldContainExactly listOf("In Progress")
        assertEquals(1, states.size)
        assertEquals("In Progress", states[0].name)
    }
}