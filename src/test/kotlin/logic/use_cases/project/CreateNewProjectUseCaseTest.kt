package logic.use_cases.project

import fakes.FakeProjectRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exceptions.AccessDeniedException
import logic.exceptions.InvalidProjectDescriptionException
import logic.exceptions.InvalidProjectTitleException
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class CreateNewProjectUseCaseTest {

    private lateinit var fakeProjectRepository: FakeProjectRepository
    private lateinit var createNewProjectUseCase: CreateNewProjectUseCase
    private lateinit var projectDataValidationUseCase: ProjectDataValidationUseCase

    @BeforeEach
    fun setup() {
        fakeProjectRepository = FakeProjectRepository()
        projectDataValidationUseCase = ProjectDataValidationUseCase()
        createNewProjectUseCase = CreateNewProjectUseCase(fakeProjectRepository, projectDataValidationUseCase)
    }

    @Test
    fun `should return the created project when admin create project seccessfully`() {
        // Given
        val userAdmin = User(username = "admin1", passwordHash = "123456", role = UserRole.ADMIN)
        val projectTitle = "Project B"
        val projectDescription = "Test Project 1"

        // When
        val result = createNewProjectUseCase.createProject(
            userAdmin,
            projectTitle,
            projectDescription,
        )

        // Then
        val storedProjects = fakeProjectRepository.getAllProjects()
        result shouldBe storedProjects[0]
    }

    @Test
    fun `should throw AccessDeniedException when user create project without admin role`() {
        // Given
        val userMate = User(username = "user", passwordHash = "123456788", role = UserRole.MATE)
        val projectTitle = "Project B"
        val projectDescription = "Test Project 2"

        // When & Then
        shouldThrow<AccessDeniedException> {
            createNewProjectUseCase.createProject(userMate, projectTitle, projectDescription)
        }
    }

    @Test
    fun `should throw InvalidProjectTitleException when project title is empty`() {
        // Given
        val userAdmin = User(username = "admin2", passwordHash = "123456", role = UserRole.ADMIN)
        val projectTitle = ""
        val projectDescription = "Test Project 2"

        // When & Then
        shouldThrow<InvalidProjectTitleException> {
            createNewProjectUseCase.createProject(userAdmin, projectTitle, projectDescription, )
        }
        fakeProjectRepository.getAllProjects() shouldHaveSize 0
    }

    @Test
    fun `should throw InvalidProjectTitleException when project title is blank`() {
        // Given
        val userAdmin = User(username = "admin2", passwordHash = "123456", role = UserRole.ADMIN)
        val projectTitle = "    "
        val projectDescription = "Test Project 2"

        // When & Then
        shouldThrow<InvalidProjectTitleException> {
            createNewProjectUseCase.createProject(userAdmin, projectTitle, projectDescription, )
        }
        fakeProjectRepository.getAllProjects() shouldHaveSize 0
    }

    @Test
    fun `should throw InvalidProjectDescriptionException when description title is empty`() {
        // Given
        val userAdmin = User(username = "admin2", passwordHash = "123456", role = UserRole.ADMIN)
        val projectTitle = "Test Project 2"
        val projectDescription = "  "

        // When & Then
        shouldThrow<InvalidProjectDescriptionException> {
            createNewProjectUseCase.createProject(userAdmin, projectTitle, projectDescription, )
        }
        fakeProjectRepository.getAllProjects() shouldHaveSize 0
    }

    @Test
    fun `should throw InvalidProjectDescriptionException when project description is blank`() {
        // Given
        val userAdmin = User(username = "admin2", passwordHash = "123456", role = UserRole.ADMIN)
        val projectTitle ="Test Project 2"
        val projectDescription = "  "

        // When & Then
        shouldThrow<InvalidProjectDescriptionException> {
            createNewProjectUseCase.createProject(userAdmin, projectTitle, projectDescription, )
        }
        fakeProjectRepository.getAllProjects() shouldHaveSize 0
    }
}