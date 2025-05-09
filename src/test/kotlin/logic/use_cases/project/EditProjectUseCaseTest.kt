package logic.use_cases.project

import fakes.FakeMongoLogRepository
import fakes.FakeProjectRepository
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.ui.utils.GenerateUUID
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class EditProjectUseCaseTest {

    private lateinit var fakeProjectRepository: FakeProjectRepository
    private lateinit var editProjectUseCase: EditProjectUseCase
    private lateinit var fakeMongoLogRepository: FakeMongoLogRepository

    @BeforeEach
    fun setup() {
        fakeProjectRepository = FakeProjectRepository()
        fakeMongoLogRepository = FakeMongoLogRepository()
        editProjectUseCase = EditProjectUseCase(fakeProjectRepository, fakeMongoLogRepository)
    }

    @Test
    fun `editProject should return true when valid project is updated`() = runTest {
        // Given
        val projectId = GenerateUUID().generate()
        val oldProject = Project(
            id = projectId,
            title = "project title",
            description = "test project 1",
            creatorUserId = "userID",
        )
        fakeProjectRepository.createNewProject(oldProject)

        val updatedProject = oldProject.copy(
            title = "Updated project Title",
            description = "Updated project Description",
        )
        val user = User(
            userId = "1",
            username = "Ahmed",
            passwordHash = "asdasdasd",
            role = UserRole.ADMIN
        )
        // When
        val result = editProjectUseCase.editProject(user, updatedProject)

        // Then
        result.shouldBeTrue()
        val stored = fakeProjectRepository.getProjectById(projectId)
        stored?.title shouldBe "Updated project Title"
        stored?.description shouldBe "Updated project Description"
    }

    @Test
    fun `editProject should return false when project list is empty`() = runTest {
        // Given
        val fakeProject = Project(
            id = GenerateUUID().generate(),
            title = "project title",
            description = "project description",
            creatorUserId = "userID",
        )
        val user = User(
            userId = "1",
            username = "Ahmed",
            passwordHash = "asdasdasd",
            role = UserRole.ADMIN
        )
        // When
        val result = editProjectUseCase.editProject(user, fakeProject)

        // Then
        result.shouldBeFalse()
    }
}