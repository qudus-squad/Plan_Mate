package logic.useCases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.useCases.tasks.CreateNewTaskUseCase
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.junit.jupiter.api.Test

import org.qudus.squad.logic.validation.TaskDataValidator


class CreateNewTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var createNewTaskUseCase: CreateNewTaskUseCase
    private lateinit var logRepository: LogRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        createNewTaskUseCase = CreateNewTaskUseCase(taskRepository,logRepository, TaskDataValidator())
    }

    @Test
    fun `createNewTask should call repository with given task`(){
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
    fun `createNewTask should throw EmptyValuesException when some arguments has empty value`(){
        // Given
        val task = Task(
            title = "Notes",
            description = " ",
            projectId = "dasda144",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = ""
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }

    @Test
    fun `createNewTask should throw EmptyValuesException when title has empty value`(){
        // Given
        val task = Task(
            title = "",
            description = "some notes",
            projectId = "dasda144",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "145826"
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }

    @Test
    fun `createNewTask should throw EmptyValuesException when project id has empty value`(){
        // Given
        val task = Task(
            title = "Note",
            description = "some notes",
            projectId = "",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "145826"
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }

    @Test
    fun `createNewTask should throw EmptyValuesException when creator id has empty value`(){
        // Given
        val task = Task(
            title = "Note",
            description = "some notes",
            projectId = "125886f",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = ""
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            createNewTaskUseCase.createNewTask("ahmed",task)
        }
    }
}