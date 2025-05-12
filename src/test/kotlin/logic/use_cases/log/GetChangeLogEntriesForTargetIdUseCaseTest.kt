package logic.use_cases.log

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.ui.utils.DateTimeFormatter

class GetChangeLogEntriesForTargetIdUseCaseTest {

    private lateinit var logRepository: LogRepository
    private lateinit var logEntryDataValidator: LogEntryDataValidationUseCase
    private lateinit var useCase: GetChangeLogEntriesForTargetIdUseCase

    @BeforeEach
    fun setUp() {
        logRepository = mockk(relaxed = true)
        logEntryDataValidator = LogEntryDataValidationUseCase()
        useCase = GetChangeLogEntriesForTargetIdUseCase(logRepository, logEntryDataValidator)
    }

    private val sampleDateTime = LocalDateTime(
        date = LocalDate(2025, 4, 30),
        time = LocalTime(2, 45)
    )

    private fun sampleLogEntries(): List<LogEntry> = listOf(
        LogEntry(
            userName = USERNAME,
            targetId = "xyz-123",
            targetType = TargetType.PROJECT,
            action = "UPDATED",
            oldValue = "old name",
            newValue = "new name",
            loggedAt = sampleDateTime
        )
    )

    @Test
    fun `should return formatted change logs for target id when history exists`() {
        runTest {
            // Given
            val targetId = "xyz-123"
            val logs = sampleLogEntries()
            coEvery { logRepository.getLogByTargetId(targetId) } returns logs

            // When
            val result = useCase.getFormattedLog(targetId)

            // Then
            result.shouldContainExactly(
                listOf(
                    FormattedLogEntry(
                        userName = USERNAME,
                        target = TARGET,
                        changeValue =VALUES ,
                        time = DateTimeFormatter.formatDateTimeForDisplay(sampleDateTime)
                    )
                )
            )
        }
    }

    @Test
    fun `should return empty list when no logs exist for target id`() {
        runTest {
            // Given
            val targetId = "5264696545"
            coEvery { logRepository.getLogByTargetId(targetId) } returns emptyList()

            // When
            val result = useCase.getFormattedLog(targetId)

            // Then
            result shouldBe emptyList()
        }
    }
    companion object{
        const val USERNAME="farah"
        const val VALUES= "old name to new name"
        const val TARGET ="project xyz-123"

    }
}
