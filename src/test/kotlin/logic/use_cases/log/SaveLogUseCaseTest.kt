package logic.use_cases.log

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import logic.exceptions.InvalidLogActionException
import logic.exceptions.InvalidLogTargetIdException
import logic.exceptions.InvalidLogUserNameException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType

class SaveLogUseCaseTest {

    private lateinit var logRepository: LogRepository
    private lateinit var logEntryDataValidator: LogEntryDataValidationUseCase
    private lateinit var saveLogUseCase: SaveLogUseCase

    @BeforeEach
    fun setup() {
        logRepository = mockk(relaxed = true)
        logEntryDataValidator = LogEntryDataValidationUseCase()
        saveLogUseCase = SaveLogUseCase(logRepository, logEntryDataValidator)
    }

    private fun dateTime() =
        LocalDateTime(LocalDate(2025, 5, 1), LocalTime(10, 30))

    @Test
    fun `should save log entry in repository when addLog called`() {
        runTest {
            // Given
            val logEntry = LogEntry(
                userName = "farah",
                targetId = "hh-ff1",
                targetType = TargetType.TASK,
                action = "CREATED",
                oldValue = null,
                newValue = "Task 1",
                loggedAt = dateTime()
            )

            // When
            saveLogUseCase.saveLog(logEntry)

            // Then
            coVerify {
                logRepository.addNewLog(logEntry)
            }
        }
    }

    @Test
    fun `should throw InvalidLogUserNameException when log has blank userName`() {
        runTest {
            val logEntry = LogEntry(
                userName = "",
                targetId = "id1",
                targetType = TargetType.TASK,
                action = "CREATED",
                oldValue = null,
                newValue = "Task1",
                loggedAt = dateTime()
            )

            shouldThrow<InvalidLogUserNameException> {
                saveLogUseCase.saveLog(logEntry)
            }
        }
    }

    @Test
    fun `should throw InvalidLogTargetIdException when log has blank targetId`() {
        runTest {
            val logEntry = LogEntry(
                userName = "farah",
                targetId = "",
                targetType = TargetType.TASK,
                action = "CREATED",
                oldValue = null,
                newValue = "Task1",
                loggedAt = dateTime()
            )

            shouldThrow<InvalidLogTargetIdException> {
                saveLogUseCase.saveLog(logEntry)
            }
        }
    }

    @Test
    fun `should throw InvalidLogActionException when log has blank action`() {
        runTest {
            val logEntry = LogEntry(
                userName = "farah",
                targetId = "id1",
                targetType = TargetType.TASK,
                action = "",
                oldValue = null,
                newValue = "Task1",
                loggedAt = dateTime()
            )

            shouldThrow<InvalidLogActionException> {
                saveLogUseCase.saveLog(logEntry)
            }
        }
    }
}
