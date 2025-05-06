package logic.use_cases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.BeforeEach
import model.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import kotlin.test.Test


class GetAllTasksByProjectIdUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var getAllTasksByProjectIdUseCase: GetAllTasksByProjectIdUseCase

    @BeforeEach
    fun setup() {
        taskRepository = mockk<TaskRepository>(relaxed = true)
        getAllTasksByProjectIdUseCase = GetAllTasksByProjectIdUseCase(taskRepository)
    }

    @Test
    fun `should return tasks by project id when tasks are found`() {
        // Given
        val task1 = Task(
            id = "1",
            title = "Task Title 1",
            description = "Task Description 1",
            projectId = "1",
            taskState = TaskState("1", "Done"),
            creatorUserID = "Creator ID",
            assignedUserId = null,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            lastUpdatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )

        val task2 = Task(
            id = "2",
            title = "Task Title 2",
            description = "Task Description 2",
            projectId = "1",
            taskState = TaskState("2", "To Do"),
            creatorUserID = "Creator ID",
            assignedUserId = null,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            lastUpdatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )

        every { taskRepository.getAllTasksByProjectId("1") } returns listOf(task1, task2)

        // When
        val result = taskRepository.getAllTasksByProjectId("1")

        // Then
        result shouldContainExactly listOf(task1, task2)
    }

    @Test
    fun `should return NoTasksFoundException when no tasks are found`() {
        // Given
        every { taskRepository.getAllTasksByProjectId("1") } returns emptyList()

        // When & Then
        shouldThrow<TaskNotFoundException> {
            getAllTasksByProjectIdUseCase.getAllTasksByProjectId("1")
        }.message shouldBe GetAllTasksByProjectIdUseCase.NO_TASK_FOUND
    }
}