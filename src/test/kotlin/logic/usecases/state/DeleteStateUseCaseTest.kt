package logic.usecases.state

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.exceptions.UnauthorizedAccessException
import org.qudus.squad.logic.usecases.state.CreateStateUseCase
import org.qudus.squad.logic.usecases.state.DeleteStateUseCase
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.User
import org.qudus.squad.model.UserRole
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
        createStateUseCase.createState(admin, projectId, "ToDo")

        // When
        val result = deleteStateUseCase.deleteState(admin, projectId, "ToDo")

        // Then
        result.shouldBeSuccess()
        stateRepository.getAllStatesForProject(projectId).shouldBeEmpty()
    }

    @Test
    fun `should fail to delete non-existent state within project states`() {

        // Given
        val admin = AdminUser("admin", "hashed")

        // When
        val result = deleteStateUseCase.deleteState(
            admin,
            projectId,
            "NonExistent",
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

        // When
        val result = deleteStateUseCase.deleteState(mate, projectId, "ToDo")

        // Then
        result.shouldBeFailure { it is UnauthorizedAccessException }
    }
}