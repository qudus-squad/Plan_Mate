package logic.useCases.taskState

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.useCases.taskState.CreateNewTaskStateUseCase
import org.qudus.squad.logic.useCases.taskState.GetAllTaskStatesByProjectIdUseCase
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import kotlin.test.Test

class GetAllTaskStatesByProjectIdUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var createNewTaskStateUseCase: CreateNewTaskStateUseCase
    private lateinit var getAllStatesUseCase: GetAllTaskStatesByProjectIdUseCase

    private val projectId1 = "P001"
    private val projectId2 = "P002"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        createNewTaskStateUseCase = CreateNewTaskStateUseCase(stateRepository)
        getAllStatesUseCase = GetAllTaskStatesByProjectIdUseCase(stateRepository)
    }

    @Test
    fun `should return all states for a given project`() {

        // Given
        val admin = User("Abdo", "123456" , role = UserRole.ADMIN)


        // When
        createNewTaskStateUseCase.createState(admin, projectId1, TaskState(name = "ToDo"))
        createNewTaskStateUseCase.createState(admin, projectId1, TaskState(name = "InProgress"))
        createNewTaskStateUseCase.createState(admin, projectId1, TaskState(name = "Done"))

        val states = getAllStatesUseCase.getAllTaskStates(projectId1)

        // Then
        states.map { it.name } shouldContainExactlyInAnyOrder listOf("ToDo", "InProgress", "Done")
    }

    @Test
    fun `should return empty list if no states exist for project`() {

        // When
        val states = getAllStatesUseCase.getAllTaskStates(projectId2)

        // Then
        states.shouldBeEmpty()
    }

    @Test
    fun `should not return states from other projects`() {

        // Given
        val admin = User("Abdo", "123456" , role = UserRole.ADMIN)

        createNewTaskStateUseCase.createState(admin, projectId1, TaskState(name = "ToDo"))
        createNewTaskStateUseCase.createState(admin, projectId2, TaskState(name = "Archived"))

        // When
        val states = getAllStatesUseCase.getAllTaskStates(projectId1)

        // Then
        states.map { it.name } shouldContainExactlyInAnyOrder listOf("ToDo")
    }
}