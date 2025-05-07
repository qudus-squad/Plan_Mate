package logic.use_cases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import logic.exceptions.TaskNotFoundException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import java.io.IOException
import kotlin.test.Test

class AssignTaskToUserUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var assignTaskToUserUseCase: AssignTaskToUserUseCase

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        assignTaskToUserUseCase = AssignTaskToUserUseCase(taskRepository)
    }

    @Test
    fun `should throw NoTasksFoundException when task is not found`() {
        runTest {
            // Given
            val taskId = "task1"
            val userId = "user1"

            coEvery { taskRepository.getTaskById(taskId) } returns null

            // When & Then
            shouldThrow<TaskNotFoundException> {
                assignTaskToUserUseCase.assignTaskToUser(userId, taskId)
            }
        }
    }

    @Test
    fun `should call assignTaskToUser and return true when task is found`() {
        runTest {
            // Given
            val taskId = "task1"
            val userId = "user1"
            val task = Task(
                id = taskId,
                assignedUserId = null,
                lastUpdatedAt = LocalDateTime.parse("2000-07-17T00:00:00"),
                title = "Task1",
                description = "Task1 des",
                projectId = "122",
                taskState = TaskState(name = "Done"),
                creatorUserID = "123456",
                createdAt = LocalDateTime.parse("2000-07-16T00:00:00")
            )

            coEvery { taskRepository.getTaskById(taskId) } returns task
            coEvery { taskRepository.assignTaskToUser(taskId, userId) } returns true

            // When
            val result = assignTaskToUserUseCase.assignTaskToUser(userId, taskId)

            // Then
            result shouldBe true
        }
    }

    @Test
    fun `should propagate exception when assignTaskToUser fails`() {
        runTest {
            // Given
            val taskId = "task1"
            val userId = "user1"
            val task = Task(
                id = taskId,
                assignedUserId = null,
                lastUpdatedAt = LocalDateTime.parse("2000-07-17T00:00:00"),
                title = "Task1",
                description = "Task1 des",
                projectId = "122",
                taskState = TaskState(name = "Done"),
                creatorUserID = "123456",
                createdAt = LocalDateTime.parse("2000-07-16T00:00:00")
            )
            coEvery { taskRepository.getTaskById(taskId) } returns task
            val exception = IOException("Failed to assign user")
            coEvery { taskRepository.assignTaskToUser(taskId, userId) } throws exception

            // When & then
            shouldThrow<IOException> {
                assignTaskToUserUseCase.assignTaskToUser(userId, taskId)
            }
        }
    }

}