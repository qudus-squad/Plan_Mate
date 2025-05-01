package logic.usecases.state

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.usecases.state.CreateStateUseCase
import org.qudus.squad.logic.usecases.state.DeleteStateUseCase
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.State
import org.qudus.squad.model.User
import org.qudus.squad.model.UserRole
import org.qudus.squad.model.exceptions.UnauthorizedAccessException
import kotlin.test.Test


class DeleteStateUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var createStateUseCase: CreateStateUseCase
    private lateinit var deleteStateUseCase: DeleteStateUseCase
    private val projectId = "P001"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        createStateUseCase = CreateStateUseCase(stateRepository)
        deleteStateUseCase = DeleteStateUseCase(stateRepository)
    }

    @Test
    fun `should allow admin to delete existing state within project states`() {

        // Given
        val admin = AdminUser("admin", "hashed")
        val state = State(name = "ToDo")
        createStateUseCase.createState(admin, projectId, state)

        // When
        val result = deleteStateUseCase.deleteState(admin, projectId, state)

        // Then
        result.shouldBeSuccess()
        stateRepository.getAllStatesForProject(projectId).shouldBeEmpty()
    }

    @Test
    fun `should fail to delete non-existent state within project states`() {

        // Given
        val admin = AdminUser("admin", "hashed")
        val nonExistentState = State(name = "NonExistent")

        // When
        val result = deleteStateUseCase.deleteState(
            admin,
            projectId,
            nonExistentState,
        )

        // Then
        result.shouldBeFailure()
    }

    @Test
    fun `should fail when delete state if user is not admin`() {

        // Given
        val mate = object : User("mate", "hashed") {
            override val role = UserRole.MATE
        }
        val state = State(name = "ToDo")

        // When
        val result = deleteStateUseCase.deleteState(mate, projectId, state)

        // Then
        result.shouldBeFailure { it is UnauthorizedAccessException }
    }
}