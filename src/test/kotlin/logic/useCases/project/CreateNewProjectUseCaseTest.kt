package logic.useCases.project

import fakes.FakeProjectRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.InvalidProjectInfo
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class CreateNewProjectUseCaseTest {

    private lateinit var fakeProjectRepository: FakeProjectRepository
    private lateinit var createNewProjectUseCase: CreateNewProjectUseCase

    @BeforeEach
    fun setup() {
        fakeProjectRepository = FakeProjectRepository()
        createNewProjectUseCase = CreateNewProjectUseCase(fakeProjectRepository)
    }

    @Test
    fun `createNewProject should be allowed for admin to create project successfully then return the created project`() {
        // Given
        val userAdmin = User(username = "admin1", passwordHash = "123456", role = UserRole.ADMIN)
        val projectTitle = "Project B"
        val projectDescription = "Test Project 1"
        val statesForProject = listOf(TaskState(name = "ToDo"), TaskState(name = "Done"))

        // When
        val result = createNewProjectUseCase.createProject(
            userAdmin,
            projectTitle,
            projectDescription,
            statesForProject,
        )

        // Then
        val storedProjects = fakeProjectRepository.getAllProjects()
        result shouldBe storedProjects[0]
    }

    @Test
    fun `createNewProject should throw AccessDeniedException when user is not admin`() {
        // Given
        val userMate = User(username = "user", passwordHash = "123456", role = UserRole.MATE)
        val projectTitle = "Project B"
        val projectDescription = "Test Project 2"
        val statesForProject = listOf(TaskState(name = "ToDo"))

        // When & Then
        shouldThrow<AccessDeniedException> {
            createNewProjectUseCase.createProject(userMate, projectTitle, projectDescription, statesForProject)
        }
    }

    @Test
    fun `createNewProject should throw InvalidProjectInfo when title is empty`() {
        // Given
        val userAdmin = User(username = "admin2", passwordHash = "123456", role = UserRole.ADMIN)
        val projectTitle = ""
        val projectDescription = "Test Project 2"
        val statesForProject = listOf(TaskState(name = "ToDo"))

        // When & Then
        shouldThrow<InvalidProjectInfo> {
            createNewProjectUseCase.createProject(userAdmin, projectTitle, projectDescription, statesForProject)
        }
        fakeProjectRepository.getAllProjects() shouldHaveSize 0
    }

}