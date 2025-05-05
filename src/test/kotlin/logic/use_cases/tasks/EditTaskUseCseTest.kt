package logic.use_cases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.exceptions.InvalidTaskCreatorUserIdException
import org.qudus.squad.logic.exceptions.InvalidTaskDescriptionException
import org.qudus.squad.logic.exceptions.InvalidTaskProjectIdException
import org.qudus.squad.logic.exceptions.InvalidTaskStateNameException
import org.qudus.squad.logic.exceptions.InvalidTaskTitleException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import kotlin.test.assertFalse

class EditTaskUseCseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var editTaskUseCse: EditTaskUseCse
    private lateinit var logRepository: LogRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        editTaskUseCse = EditTaskUseCse(taskRepository,logRepository, TaskDataValidationUseCase())
    }

    @Test
    fun `editExistingTask should delegate task editing to task repository`(){
        // Given
        val updatedTask = Task(
            title = "Notes",
            description = "some notes",
            projectId = "dasda144",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "fsdfs2356"
        )
        // When
        editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
            oldValue = "Note", newValue = "My note")
        // Then
        verify(exactly = 1) { taskRepository.editExistingTask(updatedTask = updatedTask) }
    }

    @Test
    fun `should throw InvalidTaskTitleException when title has empty value`(){
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
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

    @Test
    fun `should throw InvalidTaskProjectIdException when project id has empty value`(){
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
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

    @Test
    fun `should throw InvalidTaskCreatorUserIdException when creator id has empty value`(){
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
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

    @Test
    fun `should throw InvalidTaskDescriptionException when description has empty value`(){
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
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

    @Test
    fun `should throw InvalidTaskStateNameException when task state name has empty value`(){
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
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

}