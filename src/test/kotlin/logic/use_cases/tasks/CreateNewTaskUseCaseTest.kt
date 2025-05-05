package logic.use_cases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.InvalidTaskCreatorUserIdException
import org.qudus.squad.logic.exceptions.InvalidTaskDescriptionException
import org.qudus.squad.logic.exceptions.InvalidTaskProjectIdException
import org.qudus.squad.logic.exceptions.InvalidTaskStateNameException
import org.qudus.squad.logic.exceptions.InvalidTaskTitleException

import org.qudus.squad.logic.validation.TaskDataValidationUseCase


class CreateNewTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var createNewTaskUseCase: CreateNewTaskUseCase
    private lateinit var logRepository: LogRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        createNewTaskUseCase = CreateNewTaskUseCase(taskRepository,logRepository, TaskDataValidationUseCase())
    }

    @Test
    fun `createNewTaskUseCase should delegate task creation to task repository`(){
        // Given
        val task = Task(
            title = "Notes",
            description = "some notes",
            projectId = "dasda144",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "fsdfs2356"
        )
        // When
        createNewTaskUseCase.createNewTask("ahmed",task)
        // Then
        verify(exactly = 1) { taskRepository.createNewTask(task = task) }
    }

    @Test
    fun `should throw InvalidTaskTitleException when title has empty value`(){
        // Given
        val task = Task(
            title = "",
            description = "some notes",
            projectId = "dasda144",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "145826"
        )
        // When & Then
        shouldThrow<InvalidTaskTitleException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }

    @Test
    fun `should throw InvalidTaskProjectIdException when project id has empty value`(){
        // Given
        val task = Task(
            title = "Note",
            description = "some notes",
            projectId = "",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "145826"
        )
        // When & Then
        shouldThrow<InvalidTaskProjectIdException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }

    @Test
    fun `should throw InvalidTaskCreatorUserIdException when creator id has empty value`(){
        // Given
        val task = Task(
            title = "Note",
            description = "some notes",
            projectId = "125886f",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = ""
        )
        // When & Then
        shouldThrow<InvalidTaskCreatorUserIdException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }

    @Test
    fun `should throw InvalidTaskDescriptionException when description has empty value`(){
        // Given
        val task = Task(
            title = "Note",
            description = "  ",
            projectId = "125886f",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "14256"
        )
        // When & Then
        shouldThrow<InvalidTaskDescriptionException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }

    @Test
    fun `should throw InvalidTaskStateNameException when task state name has empty value`(){
        // Given
        val task = Task(
            title = "Note",
            description = "notes",
            projectId = "125886f",
            taskState = TaskState(name = ""),
            creatorUserID = "12563"
        )
        // When & Then
        shouldThrow<InvalidTaskStateNameException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }
}