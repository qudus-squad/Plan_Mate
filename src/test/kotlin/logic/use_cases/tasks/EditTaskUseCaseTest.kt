package logic.use_cases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exceptions.InvalidTaskCreatorUserIdException
import logic.exceptions.InvalidTaskDescriptionException
import logic.exceptions.InvalidTaskProjectIdException
import logic.exceptions.InvalidTaskStateNameException
import logic.exceptions.InvalidTaskTitleException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class EditTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var editTaskUseCase: EditTaskUseCase
    private lateinit var logRepository: LogRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        editTaskUseCase = EditTaskUseCase(taskRepository, logRepository, TaskDataValidationUseCase())
    }

    @Test
    fun `editExistingTask should delegate task editing to task repository`() {
        runTest {
            // Given
            val updatedTask = Task(
                title = "Notes",
                description = "some notes",
                projectId = "dasda144",
                taskState = TaskState(name = "To-Do"),
                creatorUserID = "fsdfs2356"
            )
            // When
            editTaskUseCase.editTask(
                userName = "sami", updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note"
            )
            // Then
            verify(exactly = 1) { taskRepository.editExistingTask(updatedTask = updatedTask) }
        }

    }

    @Test
    fun `should throw InvalidTaskTitleException when title has empty value`() {
        runTest {
            // Given
            val updatedTask = Task(
                title = "",
                description = "some notes",
                projectId = "dasda144",
                taskState = TaskState(name = "To-Do"),
                creatorUserID = "145826"
            )
            // When & Then
            shouldThrow<InvalidTaskTitleException> {
                editTaskUseCase.editTask(
                    userName = "sami", updatedTask = updatedTask, action = "edit task name",
                    oldValue = "Note", newValue = "My note"
                )
            }
        }

    }

    @Test
    fun `should throw InvalidTaskProjectIdException when project id has empty value`() {
        runTest {
            // Given
            val updatedTask = Task(
                title = "Note",
                description = "some notes",
                projectId = "",
                taskState = TaskState(name = "To-Do"),
                creatorUserID = "145826"
            )
            // When & Then
            shouldThrow<InvalidTaskProjectIdException> {
                editTaskUseCase.editTask(
                    userName = "sami", updatedTask = updatedTask, action = "edit task name",
                    oldValue = "Note", newValue = "My note"
                )
            }
        }
    }

    @Test
    fun `should throw InvalidTaskCreatorUserIdException when creator id has empty value`() {
        runTest {
            // Given
            val updatedTask = Task(
                title = "Note",
                description = "some notes",
                projectId = "125886f",
                taskState = TaskState(name = "To-Do"),
                creatorUserID = ""
            )
            // When & Then
            shouldThrow<InvalidTaskCreatorUserIdException> {
                editTaskUseCase.editTask(
                    userName = "sami", updatedTask = updatedTask, action = "edit task name",
                    oldValue = "Note", newValue = "My note"
                )
            }
        }
    }

    @Test
    fun `should throw InvalidTaskDescriptionException when description has empty value`() {
        runTest {
            // Given
            val updatedTask = Task(
                title = "Note",
                description = " ",
                projectId = "125886f",
                taskState = TaskState(name = "To-Do"),
                creatorUserID = "12584"
            )
            // When & Then
            shouldThrow<InvalidTaskDescriptionException> {
                editTaskUseCase.editTask(
                    userName = "sami", updatedTask = updatedTask, action = "edit task name",
                    oldValue = "Note", newValue = "My note"
                )
            }
        }

    }

    @Test
    fun `should throw InvalidTaskStateNameException when task state name has empty value`() {
        runTest {
            // Given
            val updatedTask = Task(
                title = "Note",
                description = "notes",
                projectId = "125886f",
                taskState = TaskState(name = ""),
                creatorUserID = "12563"
            )
            // When & Then
            shouldThrow<InvalidTaskStateNameException> {
                editTaskUseCase.editTask(
                    userName = "sami", updatedTask = updatedTask, action = "edit task name",
                    oldValue = "Note", newValue = "My note"
                )
            }
        }
    }

}