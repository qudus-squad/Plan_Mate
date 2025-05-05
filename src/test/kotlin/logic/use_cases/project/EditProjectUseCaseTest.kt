package logic.use_cases.project

import fakes.FakeProjectRepository
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.TaskState

class EditProjectUseCaseTest {

    private lateinit var fakeProjectRepository: FakeProjectRepository
    private lateinit var editProjectUseCase: EditProjectUseCase

    @BeforeEach
    fun setup() {
        fakeProjectRepository = FakeProjectRepository()
        editProjectUseCase = EditProjectUseCase(fakeProjectRepository)
    }

    @Test
    fun `editProject should return true when valid project is updated`() {
        // Given
        val projectId = GenerateUUID().generate()
        val oldProject = Project(
            id = projectId,
            title = "project title",
            description = "test project 1",
            creatorUserId = "userID",
            taskState = listOf(TaskState(name = "ToDo"))
        )
        fakeProjectRepository.createNewProject(oldProject)

        val updatedProject = oldProject.copy(
            title = "Updated project Title",
            description = "Updated project Description",
            taskState = listOf(TaskState(name = "InProgress"))
        )

        // When
        val result = editProjectUseCase.editProject(updatedProject)

        // Then
        result.shouldBeTrue()
        val stored = fakeProjectRepository.getProjectById(projectId)
        stored?.title shouldBe "Updated project Title"
        stored?.description shouldBe "Updated project Description"
        stored?.taskState?.first()?.name shouldBe "InProgress"
    }

    @Test
    fun `editProject should return false when project list is empty`() {
        // Given
        val fakeProject = Project(
            id = GenerateUUID().generate(),
            title = "project title",
            description = "project description",
            creatorUserId = "userID",
            taskState = listOf(TaskState(name = "ToDo"))
        )

        // When
        val result = editProjectUseCase.editProject(fakeProject)

        // Then
        result.shouldBeFalse()
    }

    @Test
    fun `editProject should return false when input is invalid`() {
        // Given
        val projectId : String = GenerateUUID().generate()
        val invalidProject = Project(
            id = projectId,
            title = "",
            description = "",
            creatorUserId = "admin",
            taskState = emptyList()
        )
        fakeProjectRepository.createNewProject(invalidProject)

        // When
        val result = editProjectUseCase.editProject(invalidProject)

        // Then
        result.shouldBeFalse()
    }
}