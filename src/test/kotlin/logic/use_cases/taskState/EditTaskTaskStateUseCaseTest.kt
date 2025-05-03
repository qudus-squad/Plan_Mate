package logic.use_cases.taskState

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.UnauthorizedAccessException
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class EditTaskTaskStateUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var editTaskStateUseCase: EditTaskStateUseCase
    private lateinit var createNewTaskStateUseCase: CreateNewTaskStateUseCase
    private val projectId = "P001"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        editTaskStateUseCase = EditTaskStateUseCase(stateRepository)
        createNewTaskStateUseCase = CreateNewTaskStateUseCase(stateRepository)
    }

    @Test
    fun `The admin must be allowed to edit the status of the project states`() {
        // Given
        val admin = User("Abdo", "123456" , role = UserRole.ADMIN)


        val oldTaskState = TaskState(name = "ToDo")
        createNewTaskStateUseCase.createState(admin, projectId, oldTaskState)

        val modifiedTaskState = TaskState(id = oldTaskState.id, name = "InProgress")

        // When
        val result = editTaskStateUseCase.editState(admin, projectId, oldTaskState, modifiedTaskState)

        // Then
        result.shouldBeSuccess()
        val states = stateRepository.getAllTaskStatesByProjectId(projectId)
        states.shouldHaveSize(1)
        states.map { it.name } shouldContainExactly listOf("InProgress")
        states.first().id shouldBe oldTaskState.id
    }

    @Test
    fun `should fail to edit the non-existent state of the project states`() {
        // Given
        val admin = User("Abdo", "123456" , role = UserRole.ADMIN)

        val oldTaskState = TaskState(name = "Archived")
        val newTaskState = TaskState(name = "Active")

        // When
        val result = editTaskStateUseCase.editState(
            admin, projectId,
            oldTaskState,
            newTaskState,
        )

        // Then
        result.shouldBeFailure()
    }

    @Test
    fun `should fail when editing state if a user is not admin`() {
        // Given
        val mate = User("Abdo", "123456" , role = UserRole.MATE)

        val oldTaskState = TaskState(name = "ToDo")
        val newTaskState = TaskState(name = "Doing")

        // When
        val result = editTaskStateUseCase.editState(mate, projectId, oldTaskState, newTaskState)

        // Then
        result.shouldBeFailure { it is UnauthorizedAccessException }
    }
}