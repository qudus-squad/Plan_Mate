package logic.usecases.state

import fakes.FakeStateRepository
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.usecases.state.CreateStateUseCase
import org.qudus.squad.logic.usecases.state.GetAllStatesForProjectUseCase
import org.qudus.squad.model.AdminUser
import kotlin.test.Test

class GetAllStatesForProjectUseCaseTest {

    private lateinit var stateRepository: FakeStateRepository
    private lateinit var createStateUseCase: CreateStateUseCase
    private lateinit var getAllStatesUseCase: GetAllStatesForProjectUseCase

    private val projectId1 = "P001"
    private val projectId2 = "P002"

    @BeforeEach
    fun setup() {
        stateRepository = FakeStateRepository()
        createStateUseCase = CreateStateUseCase(stateRepository)
        getAllStatesUseCase = GetAllStatesForProjectUseCase(stateRepository)
    }

    @Test
    fun `should return all states for a given project`() {

        // Given
        val admin = AdminUser("admin", "hashed")

        // When
        createStateUseCase.createState(admin, projectId1, "ToDo")
        createStateUseCase.createState(admin, projectId1, "InProgress")
        createStateUseCase.createState(admin, projectId1, "Done")

        val states = getAllStatesUseCase.getAllStatesForProject(projectId1)

        // Then
        states.map { it.name } shouldContainExactlyInAnyOrder listOf("ToDo", "InProgress", "Done")
    }

    @Test
    fun `should return empty list if no states exist for project`() {

        // When
        val states = getAllStatesUseCase.getAllStatesForProject(projectId2)

        // Then
        states.shouldBeEmpty()
    }

    @Test
    fun `should not return states from other projects`() {

        // Given
        val admin = AdminUser("admin", "hashed")
        createStateUseCase.createState(admin, projectId1, "ToDo")
        createStateUseCase.createState(admin, projectId2, "Archived")

        // When
        val states = getAllStatesUseCase.getAllStatesForProject(projectId1)

        // Then
        states.map { it.name } shouldContainExactlyInAnyOrder listOf("ToDo")
    }
}