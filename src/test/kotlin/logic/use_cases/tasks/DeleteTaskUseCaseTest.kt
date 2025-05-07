package logic.use_cases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import logic.exceptions.InvalidTaskIdException
import logic.exceptions.InvalidTaskTitleException
import logic.exceptions.InvalidUserNameException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase

class DeleteTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var logRepository: LogRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup(){
        taskRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        deleteTaskUseCase = DeleteTaskUseCase(taskRepository,logRepository, TaskDataValidationUseCase())
    }

    @Test
    fun `deleteTask should delegate task deletion to task repository`(){
        runTest {
            // Given
            val userName = "hazem"
            val taskId = "147825"
            val taskTitle = "My Favourites"
            // when
            deleteTaskUseCase.deleteTask(userName, taskId, taskTitle)
            // Then
            coVerify(exactly = 1) { taskRepository.deleteTaskById(taskId) }
        }

    }

    @Test
    fun `should throw InvalidTaskIdException when task id is empty value`(){
        runTest {
            // Given
            val userName = "hazem"
            val taskId = " "
            val taskTitle = "My Favourites"
            // Then & when
            shouldThrow<InvalidTaskIdException> {
                deleteTaskUseCase.deleteTask(userName, taskId, taskTitle)
            }
        }

    }

    @Test
    fun `should throw InvalidUserNameException when username is empty value`(){
        runTest {
            // Given
            val userName = ""
            val taskId = "1425836"
            val taskTitle = "My Favourites"
            // Then & when
            shouldThrow<InvalidUserNameException> {
                deleteTaskUseCase.deleteTask(userName, taskId, taskTitle)
            }
        }
    }

    @Test
    fun `should throw InvalidTaskTitleException when task title is empty value`(){
        runTest {
            // Given
            val userName = "hazem"
            val taskId = "1425836"
            val taskTitle = " "
            // Then & when
            shouldThrow<InvalidTaskTitleException> {
                deleteTaskUseCase.deleteTask(userName, taskId, taskTitle)
            }
        }

    }
}