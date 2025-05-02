package logic.useCases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.useCases.tasks.EditTaskUseCse
import org.qudus.squad.logic.validation.TaskDataValidator
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class EditTaskUseCseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var editTaskUseCse: EditTaskUseCse
    private lateinit var logRepository: LogRepository

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        editTaskUseCse = EditTaskUseCse(taskRepository,logRepository, TaskDataValidator())
    }

    @Test
    fun `editExistingTask should call repository with updated task`(){
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
    fun `editExistingTask should throw EmptyValuesException when some arguments has empty value`(){
        // Given
        val updatedTask = Task(
            title = "Notes",
            description = " ",
            projectId = "dasda144",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = ""
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

    @Test
    fun `editExistingTask should throw EmptyValuesException when title has empty value`(){
        // Given
        val updatedTask = Task(
            title = "",
            description = "some notes",
            projectId = "dasda144",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "145826"
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

    @Test
    fun `editExistingTask should throw EmptyValuesException when project id has empty value`(){
        // Given
        val updatedTask = Task(
            title = "Note",
            description = "some notes",
            projectId = "",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = "145826"
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }

    @Test
    fun `editExistingTask should throw EmptyValuesException when creator id has empty value`(){
        // Given
        val updatedTask = Task(
            title = "Note",
            description = "some notes",
            projectId = "125886f",
            taskState = TaskState(name = "To-Do"),
            creatorUserID = ""
        )
        // When & Then
        shouldThrow<EmptyValuesException> {
            editTaskUseCse.editTask(userName = "sami",updatedTask = updatedTask, action = "edit task name",
                oldValue = "Note", newValue = "My note")
        }
    }
}