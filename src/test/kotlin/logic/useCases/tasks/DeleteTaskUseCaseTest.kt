package logic.useCases.tasks

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.EmptyValuesException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.useCases.tasks.DeleteTaskUseCase
import org.qudus.squad.logic.validation.TaskDataValidator

class DeleteTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var logRepository: LogRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @BeforeEach
    fun setup(){
        taskRepository = mockk(relaxed = true)
        logRepository = mockk(relaxed = true)
        deleteTaskUseCase = DeleteTaskUseCase(taskRepository,logRepository, TaskDataValidator())
    }

    @Test
    fun `deleteTask should call delete function in task repository when values validated`(){
        // Given
        val userName = "hazem"
        val taskId = "147825"
        val taskName = "My Favourites"
         // when
        deleteTaskUseCase.deleteTask(userName, taskId, taskName)
        // Then
        verify(exactly = 1) { taskRepository.deleteTaskById(taskId) }
    }

    @Test
    fun `deleteTask should throw exception when task id is empty value`(){
        // Given
        val userName = "hazem"
        val taskId = " "
        val taskName = "My Favourites"
        // Then & when
        shouldThrow<EmptyValuesException> {
            deleteTaskUseCase.deleteTask(userName, taskId, taskName)
        }
    }

    @Test
    fun `deleteTask should throw exception when user name is empty value`(){
        // Given
        val userName = ""
        val taskId = "1425836"
        val taskName = "My Favourites"
        // Then & when
        shouldThrow<EmptyValuesException> {
            deleteTaskUseCase.deleteTask(userName, taskId, taskName)
        }
    }

    @Test
    fun `deleteTask should throw exception when task name is empty value`(){
        // Given
        val userName = "hazem"
        val taskId = "1425836"
        val taskName = " "
        // Then & when
        shouldThrow<EmptyValuesException> {
            deleteTaskUseCase.deleteTask(userName, taskId, taskName)
        }
    }
}