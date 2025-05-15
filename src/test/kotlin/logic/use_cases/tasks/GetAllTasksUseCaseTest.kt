package logic.use_cases.tasks

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.tasks.GetAllTasksUseCase
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class GetAllTasksUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var getAllTasksUseCase: GetAllTasksUseCase

    @BeforeEach
    fun setup() {
        taskRepository = mockk(relaxed = true)
        getAllTasksUseCase = GetAllTasksUseCase(taskRepository)
    }

    @Test
    fun `should return empty list when no tasks exist`() = runTest {
        // Given
        coEvery { taskRepository.getAllTasks() } returns emptyList()

        // When
        val projects = getAllTasksUseCase.getAllTasks()

        // Then
        projects shouldBe emptyList()
    }

    @Test
    fun `should return tasks when there are tasks`() = runTest {
        // Given
        val tasks = listOf<Task>(
            Task(
                id = "Task1Id",
                title = "Task1",
                description = "Description",
                projectId = "projectId",
                taskState = TaskState(
                    name = "Done"
                ),
                creatorUserID = "2233355588999",
                assignedUserId = "223366997788",
            ),
            Task(
                id = "Task2Id",
                title = "Task1",
                description = "Description",
                projectId = "projectId",
                taskState = TaskState(
                    name = "Done"
                ),
                creatorUserID = "2233355588999",
                assignedUserId = "223366997788",
            )
        )
        coEvery { taskRepository.getAllTasks() } returns tasks

        // When
        val projects = getAllTasksUseCase.getAllTasks()

        // Then
        projects.map { task -> task.id } shouldContainExactly listOf("Task1Id", "Task2Id")
    }
}