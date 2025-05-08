package logic.use_cases.tasks


import io.kotest.assertions.throwables.shouldThrow
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exceptions.InvalidTaskIdException
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.use_cases.tasks.UnAssignTaskUseCase
import kotlin.test.Test

class UnAssignTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var unAssignTaskUseCase: UnAssignTaskUseCase

    @BeforeEach
    fun setup(){
        taskRepository = mockk(relaxed = true)
        unAssignTaskUseCase = UnAssignTaskUseCase(taskRepository)
    }

    @Test
    fun `should delegate un assign task to task repository`(){
        runTest {
            // Given
            val taskId = "1458dadsad"
            // When
            unAssignTaskUseCase.unAssignTask(taskId)
            // Then
            coVerify(exactly = 1) { taskRepository.unAssignTask(taskId) }
        }
    }

    @Test
    fun `should throw InvalidTaskIdException when task id is blank`(){
        runTest {
            // Given
            val taskId = " "
            // When & Then
            shouldThrow<InvalidTaskIdException> { unAssignTaskUseCase.unAssignTask(taskId) }
        }
    }
}