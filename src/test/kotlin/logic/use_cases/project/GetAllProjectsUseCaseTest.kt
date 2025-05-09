package logic.use_cases.project

import fakes.FakeMongoLogRepository
import fakes.FakeProjectRepository
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class GetAllProjectsUseCaseTest {

    private lateinit var projectRepository: FakeProjectRepository
    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
    private lateinit var createNewProjectUseCase: CreateNewProjectUseCase
    private lateinit var projectDataValidationUseCase: ProjectDataValidationUseCase
    private lateinit var fakeMongoLogRepository: FakeMongoLogRepository

    private val adminUser = User(
        username = "admin",
        passwordHash = "123456",
        role = UserRole.ADMIN,
    )

    @BeforeEach
    fun setup() {
        projectRepository = FakeProjectRepository()
        getAllProjectsUseCase = GetAllProjectsUseCase(projectRepository)
        projectDataValidationUseCase = ProjectDataValidationUseCase()
        fakeMongoLogRepository = FakeMongoLogRepository()
        createNewProjectUseCase = CreateNewProjectUseCase(projectRepository, projectDataValidationUseCase, fakeMongoLogRepository)
    }

    @Test
    fun `getAllProjects should return empty list when no projects exist`() = runTest {
        // When
        val projects = getAllProjectsUseCase.getAllProjects()

        // Then
        projects.shouldBeEmpty()
    }

    @Test
    fun `getAllProjects should return all created projects`() = runTest {
        // Given
        val project1 = createNewProjectUseCase.createProject(adminUser, "Project A", "test project A")
        val project2 = createNewProjectUseCase.createProject(adminUser, "Project B", "test project B")

        // When
        val projects = getAllProjectsUseCase.getAllProjects()

        // Then
        projects.map { project -> project.id } shouldContainExactly listOf(project1.id, project2.id)
    }

}