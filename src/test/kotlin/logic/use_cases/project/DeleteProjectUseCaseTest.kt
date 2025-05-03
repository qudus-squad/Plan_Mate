package logic.use_cases.project

import fakes.FakeProjectRepository
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
    fun `deleteProject should remove project by ID`() {
        // When
        deleteProjectUseCase.deleteProjectUseCase(projectId)

        // Then
        val projects = fakeProjectRepository.getAllProjects()
        projects.shouldBeEmpty()
    }

    @Test
    fun `deleteProject should not affect other projects`() {
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
        deleteProjectUseCase.deleteProjectUseCase(projectId)

        // Then
        val otherProjects = fakeProjectRepository.getAllProjects()
        otherProjects.map { project -> project.id } shouldContainExactly listOf("P002")
    }

}