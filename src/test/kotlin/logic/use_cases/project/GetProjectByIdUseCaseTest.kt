package logic.use_cases.project

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exceptions.ProjectNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource.Companion.PROJECT_NOT_FOUND
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.use_cases.project.GetProjectByIdUseCase
import org.qudus.squad.model.entity.Project

class GetProjectByIdUseCaseTest {

    private lateinit var projectRepository: ProjectRepository
    private lateinit var getProjectByIdUseCase: GetProjectByIdUseCase

    @BeforeEach
    fun setup() {
        projectRepository = mockk(relaxed = true)
        getProjectByIdUseCase = GetProjectByIdUseCase(projectRepository)
    }

    @Test
    fun `should return project when project is found`() = runTest {

        // Given
        val project = Project(
            title = "project",
            description = "title",
        )
        coEvery { projectRepository.getProjectById(project.id) } returns project

        // When
        val result = getProjectByIdUseCase.getProjectById(project.id)

        // Then
        result.title shouldBe "project"
    }

    @Test
    fun `should throw ProjectNotFoundException when there are no project with selected id`() = runTest {

        // Given
        coEvery {
            projectRepository.getProjectById("12345689")
        } throws ProjectNotFoundException(PROJECT_NOT_FOUND)

        // When & Then
        shouldThrow<ProjectNotFoundException> {
            getProjectByIdUseCase.getProjectById("12345689")
        }
    }

}

