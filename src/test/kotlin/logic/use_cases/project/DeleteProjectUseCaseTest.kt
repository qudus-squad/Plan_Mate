package logic.use_cases.project

import fakes.FakeMongoLogRepository
import fakes.FakeProjectRepository
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.model.Project
import org.qudus.squad.logic.model.User
import org.qudus.squad.logic.model.UserRole
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import kotlin.test.Test

class DeleteProjectUseCaseTest {
    private lateinit var fakeProjectRepository: FakeProjectRepository
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase
    private val projectId = "P001"
    private lateinit var fakeMongoLogRepository: FakeMongoLogRepository
    private lateinit var userDataValidationUseCase: UserDataValidationUseCase

    @BeforeEach
    fun setup() {
        fakeProjectRepository = FakeProjectRepository()
        fakeMongoLogRepository = FakeMongoLogRepository()
        userDataValidationUseCase = UserDataValidationUseCase()
        deleteProjectUseCase =
            DeleteProjectUseCase(fakeProjectRepository, fakeMongoLogRepository, userDataValidationUseCase)

        // start with initial one project
        val project = Project(
            id = projectId,
            title = "Project A",
            description = "Test Project",
            creatorUserId = "admin123",
        )
        runTest {
            fakeProjectRepository.createNewProject(project)
        }
    }

    @Test
    fun `should return true when remove project by ID and the project deleted successfully`() = runTest {
        val user = User(
            userId = "1",
            username = "Ahmed",
            passwordHash = "asdasdasd",
            role = UserRole.ADMIN
        )
        // When
        val result = deleteProjectUseCase.deleteProject(user, projectId)

        // Then
        result.shouldBeTrue()
        val projects = fakeProjectRepository.getAllProjects()
        projects.shouldBeEmpty()
    }

    @Test
    fun `should return true when project deleted and not affect other projects`() = runTest {
        // Given
        val otherProject = Project(
            id = "P002",
            title = "Another Project",
            description = "Another",
            creatorUserId = "admin456",
        )
        val user = User(
            userId = "1",
            username = "Ahmed",
            passwordHash = "asdasdasd",
            role = UserRole.ADMIN
        )
        fakeProjectRepository.createNewProject(otherProject)

        // When
        val result = deleteProjectUseCase.deleteProject(
            user, projectId
        )

        // Then
        result.shouldBeTrue()
        val otherProjects = fakeProjectRepository.getAllProjects()
        otherProjects.map { project -> project.id } shouldContainExactly listOf("P002")
    }

    @Test
    fun `should return false when delete non-existent project`() = runTest {
        // Given
        val nonExistentProjectId = "P999"
        val user = User(
            userId = "1",
            username = "Ahmed",
            passwordHash = "asdasdasd",
            role = UserRole.ADMIN
        )

        // When
        val result = deleteProjectUseCase.deleteProject(user, nonExistentProjectId)

        // Then
        result.shouldBeFalse()
        val projects = fakeProjectRepository.getAllProjects()
        projects.map { project -> project.id } shouldContainExactly listOf(projectId)
    }

}