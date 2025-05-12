package logic.use_cases.log

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import logic.exceptions.InvalidLogActionException
import logic.exceptions.InvalidLogTargetIdException
import logic.exceptions.InvalidLogUserNameException
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.TargetType
import kotlin.test.Test

class GetAllLogsUseCaseTest {


    private lateinit var logRepository: LogRepository
    private lateinit var logEntryDataValidator: LogEntryDataValidationUseCase
    private lateinit var getAllLogsUseCase: GetAllLogsUseCase

    @BeforeEach
    fun setup() {
        logRepository = mockk(relaxed = true)
        logEntryDataValidator = LogEntryDataValidationUseCase()
        getAllLogsUseCase = GetAllLogsUseCase(logRepository, logEntryDataValidator)
    }

    private fun dateTime() = LocalDateTime(LocalDate(year = 2025, monthNumber = 5, dayOfMonth = 1), LocalTime(hour = 10, minute = 30))

    @Test
    fun `should return logs when repository has valid logs`() {
        runTest {
            // Given
            val logs = listOf(
                LogEntry(
                    userName = "farah",
                    targetId = "id1",
                    TargetType.TASK,
                    action = "CREATED",
                    oldValue = null,
                    newValue = "Task1",
                    dateTime()
                ),
                LogEntry(
                    userName = "heba",
                    targetId = "id2",
                    TargetType.PROJECT,
                    action = "DELETED",
                    oldValue = "ProjX",
                    newValue = null,
                    dateTime()
                )
            )
            coEvery { logRepository.getAllLogs() } returns logs

            // When
            val result = getAllLogsUseCase.getAllLogs()

            // Then
            result.shouldContainExactly(logs)
        }
    }


    @Test
    fun `should return empty list when repository is empty`() {
        runTest {
            // Given
            coEvery { logRepository.getAllLogs() } returns emptyList()

            // When
            val result = getAllLogsUseCase.getAllLogs()

            // Then
            result shouldBe emptyList()
        }
    }

    @Test
    fun `should throw InvalidLogUserNameException when log has blank userName`() {
        runTest {
            // Given
            val logs = listOf(
                LogEntry(
                    userName = "",
                    targetId = "id1",
                    TargetType.TASK,
                    action = "CREATED",
                    oldValue = null,
                    newValue = "Task1",
                    dateTime()
                )
            )
            coEvery { logRepository.getAllLogs() } returns logs

            // When & Then
            shouldThrow<InvalidLogUserNameException> {
                getAllLogsUseCase.getAllLogs()
            }
        }
    }

    @Test
    fun `should throw InvalidLogTargetIdException when log has blank targetId`() {
        runTest {
            // Given
            val logs = listOf(
                LogEntry(
                    userName = "farah",
                    targetId = "",
                    TargetType.TASK,
                    action = "CREATED",
                    oldValue = null,
                    newValue = "Task1",
                    dateTime()
                )
            )
            coEvery { logRepository.getAllLogs() } returns logs

            // When & Then
            shouldThrow<InvalidLogTargetIdException> {
                getAllLogsUseCase.getAllLogs()
            }
        }
    }

    @Test
    fun `should throw InvalidLogActionException when log has blank action`() {
        runTest {
            // Given
            val logs = listOf(
                LogEntry(
                    userName = "farah",
                    targetId = "id1",
                    TargetType.TASK,
                    action = "",
                    oldValue = null,
                    newValue = "Task1",
                    dateTime()
                )
            )
            coEvery { logRepository.getAllLogs() } returns logs

            // When & Then
            shouldThrow<InvalidLogActionException> {
                getAllLogsUseCase.getAllLogs()
            }
        }
    }
}

