package logic.usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.string.shouldNotContain
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource.Companion.PROJECTS_FILE
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.usecases.project.CreateNewProjectUseCase
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.MateUser
import org.qudus.squad.model.State
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
        val state: List<State> = listOf()
        val projectCreator = MateUser("user", ("123456"))
        // When && Then
        shouldThrow<AccessDeniedException> {
            createNewProjectUseCase.createProject(
                title = title,
                description = description,
                user = projectCreator,
                state = state
            )
        }
    }
    @Test
    fun `should throw InvalidProjectDataException when project name is empty `() {
        //Given
        val title = ""
        val description = "new project "
        val state: List<State> = listOf()
        val projectCreator = MateUser("user", ("123456"))
        shouldThrow<AccessDeniedException> {
            createNewProjectUseCase.createProject(
                title = title,
                description = description,
                user = projectCreator,
                state = state
            )
        }
    }
    @Test
    fun `should create and store project when all requirement are filled`(){
    //Given
    val title = "project "
    val description = "new project "
    val state: List<State> = listOf()
    val projectCreator = AdminUser("user", ("123456"))
        //When
        val result =createNewProjectUseCase.createProject(
            title = title,
            description = description,
            user = projectCreator,
            state = state
        )
        //Then
        val tempCsv = PROJECTS_FILE
        tempCsv shouldNotContain title
    }
}