package logic.use_cases.taskState

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import org.junit.jupiter.api.BeforeEach
import logic.exceptions.UnauthorizedAccessException
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import kotlin.test.Test


class DeleteTaskTaskStateUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var createNewTaskStateUseCase: CreateNewTaskStateUseCase
    private lateinit var deleteTaskStateUseCase: DeleteTaskStateUseCase
    private val projectId = "P001"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        createNewTaskStateUseCase = CreateNewTaskStateUseCase(stateRepository)
        deleteTaskStateUseCase = DeleteTaskStateUseCase(stateRepository)
    }

    @Test
    fun `should allow admin to delete existing state within project states`() {

        // Given
        val admin = User("Abdo", "123456" , role = UserRole.ADMIN)
        val taskState = TaskState(name = "ToDo")
        createNewTaskStateUseCase.createState(admin, projectId, taskState)

        // When
        val result = deleteTaskStateUseCase.deleteState(admin, projectId, taskState)

        // Then
        result.shouldBeSuccess()
        stateRepository.getAllTaskStatesByProjectId(projectId).shouldBeEmpty()
    }

    @Test
    fun `should fail to delete non-existent state within project states`() {

        // Given
        val admin = User("Abdo", "123456" , role = UserRole.ADMIN)
        val nonExistentTaskState = TaskState(name = "NonExistent")

        // When
        val result = deleteTaskStateUseCase.deleteState(
            admin,
            projectId,
            nonExistentTaskState,
        )

        // Then
        result.shouldBeFailure()
    }

    @Test
    fun `should fail when delete state if user is not admin`() {

        // Given
        val mate = User("mate", "hashed" , role = UserRole.MATE)
        val taskState = TaskState(name = "ToDo")

        // When
        val result = deleteTaskStateUseCase.deleteState(mate, projectId, taskState)

        // Then
        result.shouldBeFailure { it is UnauthorizedAccessException }
    }
}