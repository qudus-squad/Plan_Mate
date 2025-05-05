package logic.use_cases.project

import fakes.FakeProjectRepository
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.TaskState
import kotlin.test.Test

class DeleteProjectUseCaseTest {
    private lateinit var fakeProjectRepository: FakeProjectRepository
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase
    private val projectId = "P001"

    @BeforeEach
    fun setup() {
        fakeProjectRepository = FakeProjectRepository()
        deleteProjectUseCase = DeleteProjectUseCase(fakeProjectRepository)

        // start with initial one project
        val project = Project(
            id = projectId,
            title = "Project A",
            description = "Test Project",
            creatorUserId = "admin123",
            taskState = listOf(TaskState(name = "ToDo"))
        )
        fakeProjectRepository.createNewProject(project)
    }

    @Test
    fun `deleteProject should remove project by ID and return true`() {
        // When
        val result = deleteProjectUseCase.deleteProjectUseCase(projectId)

        // Then
        result.shouldBeTrue()
        val projects = fakeProjectRepository.getAllProjects()
        projects.shouldBeEmpty()
    }

    @Test
    fun `deleteProject should not affect other projects and return true`() {
        // Given
        val otherProject = Project(
            id = "P002",
            title = "Another Project",
            description = "Another",
            creatorUserId = "admin456",
            taskState = listOf(TaskState(name = "InProgress"))
        )
        fakeProjectRepository.createNewProject(otherProject)

        // When
        val result = deleteProjectUseCase.deleteProjectUseCase(projectId)

        // Then
        result.shouldBeTrue()
        val otherProjects = fakeProjectRepository.getAllProjects()
        otherProjects.map { project -> project.id } shouldContainExactly listOf("P002")
    }

    @Test
    fun `deleteProject should return false for non-existent project ID`() {
        // Given
        val nonExistentProjectId = "P999"

        // When
        val result = deleteProjectUseCase.deleteProjectUseCase(nonExistentProjectId)

        // Then
        result.shouldBeFalse()
        val projects = fakeProjectRepository.getAllProjects()
        projects.map { project -> project.id } shouldContainExactly listOf(projectId)
    }

}