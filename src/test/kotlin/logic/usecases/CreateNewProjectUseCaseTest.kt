package logic.useCases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldNotContain
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource.Companion.PROJECTS_FILE
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.useCases.project.CreateNewProjectUseCase

import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.model.exceptions.AccessDeniedException

class CreateNewProjectUseCaseTest {

    private lateinit var projectRepository: ProjectRepository
    private lateinit var createNewProjectUseCase: CreateNewProjectUseCase

    @BeforeEach
    fun setup ( ) {
        projectRepository = mockk(relaxed = true)
        createNewProjectUseCase = CreateNewProjectUseCase(projectRepository)
    }

    @Test
    fun `should throw AccessDeniedException exception when user is not admin`() {
        //Given
        val title = "project "
        val description = "new project "
        val state: List<TaskState> = listOf()
        val projectCreator = User("user", ("123456"), role =UserRole.MATE )
        // When && Then
        shouldThrow<AccessDeniedException> {
            createNewProjectUseCase.createProject(
                title = title,
                description = description,
                user = projectCreator,
                taskState = state
            )
        }
    }
    @Test
    fun `should throw InvalidProjectDataException when project name is empty `() {
        //Given
        val title = ""
        val description = "new project "
        val state: List<TaskState> = listOf()
        val projectCreator = User("user", ("123456"), role =UserRole.MATE )
        shouldThrow<AccessDeniedException> {
            createNewProjectUseCase.createProject(
                title = title,
                description = description,
                user = projectCreator,
                taskState = state
            )
        }
    }
    @Test
    fun `should create and store project when all requirement are filled`(){
    //Given
    val title = "project "
    val description = "new project "
        val state: List<TaskState> = listOf()
        val projectCreator = User("user", ("123456"), role =UserRole.MATE )
        //When
        val result =createNewProjectUseCase.createProject(
            title = title,
            description = description,
            user = projectCreator,
            taskState = state
        )
        //Then
        val tempCsv = PROJECTS_FILE
        tempCsv shouldNotContain title
    }
}