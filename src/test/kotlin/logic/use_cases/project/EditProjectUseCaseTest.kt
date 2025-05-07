package logic.use_cases.project

import fakes.FakeProjectRepository
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Project

class EditProjectUseCaseTest {

    private lateinit var fakeProjectRepository: FakeProjectRepository
    private lateinit var editProjectUseCase: EditProjectUseCase

    @BeforeEach
    fun setup() {
        fakeProjectRepository = FakeProjectRepository()
        editProjectUseCase = EditProjectUseCase(fakeProjectRepository)
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

        // When
        val result = editProjectUseCase.editProject(updatedProject)

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

        // When
        val result = editProjectUseCase.editProject(fakeProject)

        // Then
        result.shouldBeFalse()
    }
}