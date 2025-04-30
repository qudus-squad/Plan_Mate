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
import org.qudus.squad.model.State

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
        val state = State(name = "InProgress")

        // When
        val result = createStateUseCase.createState(admin, projectId, state)

        // Then
        result.shouldBeSuccess()
        val states = stateRepository.getAllStatesForProject(projectId)
        states.shouldHaveSize(1)
        states.map { it.name } shouldContainExactly listOf("InProgress")
        assertEquals(1, states.size)
        assertEquals("InProgress", states[0].name)
    }
}