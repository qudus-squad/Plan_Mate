package logic.usecases.state

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.model.exceptions.UnauthorizedAccessException
import org.qudus.squad.logic.usecases.state.CreateStateUseCase
import org.qudus.squad.logic.usecases.state.EditStateUseCase
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.State
import org.qudus.squad.model.User
import org.qudus.squad.model.UserRole

class EditStateUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var editStateUseCase: EditStateUseCase
    private lateinit var createStateUseCase: CreateStateUseCase
    private val projectId = "P001"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        editStateUseCase = EditStateUseCase(stateRepository)
        createStateUseCase = CreateStateUseCase(stateRepository)
    }

    @Test
    fun `The admin must be allowed to edit the status of the project states`() {
        // Given
        val admin = AdminUser("admin", "pass")

        val oldState = State(name = "ToDo")
        createStateUseCase.createState(admin, projectId, oldState)

        val modifiedState = State(id = oldState.id, name = "InProgress")

        // When
        val result = editStateUseCase.editState(admin, projectId, oldState, modifiedState)

        // Then
        result.shouldBeSuccess()
        val states = stateRepository.getAllStatesForProject(projectId)
        states.shouldHaveSize(1)
        states.map { it.name } shouldContainExactly listOf("InProgress")
        states.first().id shouldBe oldState.id
    }

    @Test
    fun `should fail to edit the non-existent state of the project states`() {
        // Given
        val admin = AdminUser("admin", "passwordHash")
        val oldState = State(name = "Archived")
        val newState = State(name = "Active")

        // When
        val result = editStateUseCase.editState(
            admin, projectId,
            oldState,
            newState,
        )

        // Then
        result.shouldBeFailure()
    }

    @Test
    fun `should fail when editing state if a user is not admin`() {
        // Given
        val mate = object : User("mate", "hashed") {
            override val role = UserRole.MATE
        }
        val oldState = State(name = "ToDo")
        val newState = State(name = "Doing")

        // When
        val result = editStateUseCase.editState(mate, projectId, oldState, newState)

        // Then
        result.shouldBeFailure { it is UnauthorizedAccessException }
    }
}