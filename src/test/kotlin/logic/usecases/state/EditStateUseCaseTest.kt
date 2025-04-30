package logic.usecases.state

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.UnauthorizedAccessException
import org.qudus.squad.logic.usecases.state.CreateStateUseCase
import org.qudus.squad.logic.usecases.state.EditStateUseCase
import org.qudus.squad.model.AdminUser
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
    fun `The admin must be allowed to edit the status of the project statuses`() {
        // Given
        val admin = AdminUser("admin", "passwordHash")
        createStateUseCase.createState(admin, projectId, "ToDo")

        // When
        val result = editStateUseCase.editState(
            admin,
            projectId,
            "ToDo",
            "InProgress",
        )

        // Then
        result.shouldBeSuccess()
        val states = stateRepository.getAllStatesForProject(projectId)
        states.shouldHaveSize(1)
        states.map { it.name } shouldContainExactly listOf("InProgress")
    }

    @Test
    fun `should fail to edit the non-existent state of the project states`() {
        // Given
        val admin = AdminUser("admin", "passwordHash")

        // When
        val result = editStateUseCase.editState(
            admin, projectId,
            "Archived",
            "Active",
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

        // When
        val result = editStateUseCase.editState(mate, projectId, "ToDo", "Doing")

        // Then
        result.shouldBeFailure { it is UnauthorizedAccessException }
    }
}