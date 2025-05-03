package logic.use_cases.taskState

import fakes.FakeStateRepository
import org.junit.jupiter.api.Assertions.*
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.result.shouldBeSuccess
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class CreateNewTaskTaskStateUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var createNewTaskStateUseCase: CreateNewTaskStateUseCase
    private val projectId = "P001"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        createNewTaskStateUseCase = CreateNewTaskStateUseCase(stateRepository)
    }

    @Test
    fun `should allow admin to create new state`() {
        // Given
        val admin = User(username = "admin", passwordHash = "hashed", role = UserRole.ADMIN)
        val taskState = TaskState(name = "InProgress")

        // When
        val result = createNewTaskStateUseCase.createState(admin, projectId, taskState)

        // Then
        result.shouldBeSuccess()
        val states = stateRepository.getAllTaskStatesByProjectId(projectId)
        states.shouldHaveSize(1)
        states.map { it.name } shouldContainExactly listOf("InProgress")
        assertEquals(1, states.size)
        assertEquals("InProgress", states[0].name)
    }
}