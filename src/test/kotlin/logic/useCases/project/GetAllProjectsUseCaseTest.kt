package logic.useCases.project

import fakes.FakeProjectRepository
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class GetAllProjectsUseCaseTest {

    private lateinit var projectRepository: FakeProjectRepository
    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
    private lateinit var createNewProjectUseCase: CreateNewProjectUseCase

    private val adminUser = User(
        username = "admin",
        passwordHash = "123456",
        role = UserRole.ADMIN,
    )

    @BeforeEach
    fun setup() {
        projectRepository = FakeProjectRepository()
        getAllProjectsUseCase = GetAllProjectsUseCase(projectRepository)
        createNewProjectUseCase = CreateNewProjectUseCase(projectRepository)
    }

    @Test
    fun `getAllProjects should return empty list when no projects exist`() {
        // When
        val projects = getAllProjectsUseCase.getAllProjectsUseCase()

        // Then
        projects.shouldBeEmpty()
    }

    @Test
    fun `getAllProjects should return all created projects`() {
        // Given
        val projectStates = listOf(TaskState(name = "ToDo"), TaskState(name = "Done"))
        val project1 = createNewProjectUseCase.createProject(adminUser, "Project A", "test project A", projectStates)
        val project2 = createNewProjectUseCase.createProject(adminUser, "Project B", "test project B", projectStates)

        // When
        val projects = getAllProjectsUseCase.getAllProjectsUseCase()

        // Then
        projects.map { project -> project.id } shouldContainExactly listOf(project1.id, project2.id)
    }

}