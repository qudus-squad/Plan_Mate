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
import org.qudus.squad.logic.utils.DateTimeFormatter
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType

class GetChangeLogEntriesForTargetIdUseCaseTest {

    private lateinit var logRepository: LogRepository
    private lateinit var getChangeLogEntriesForTargetIdUseCase: GetChangeLogEntriesForTargetIdUseCase

    @BeforeEach
    fun setUp() {
        logRepository = mockk(relaxed = true)
        getChangeLogEntriesForTargetIdUseCase = GetChangeLogEntriesForTargetIdUseCase(logRepository)
    }

    private fun sampleLogEntries(): List<LogEntry> {
        val dateTime = LocalDateTime(
            date = LocalDate(2025, 4, 30), time = LocalTime(2, 45)
        )

        return listOf(
            LogEntry(
                userName = "farah",
                targetId = "xyz-123",
                targetType = TargetType.PROJECT,
                action = "UPDATED",
                oldValue = "old name",
                newValue = "new name",
                loggedAt = dateTime
            ), LogEntry(
                userName = "heba",
                targetId = "xyz-123",
                targetType = TargetType.PROJECT,
                action = "UPDATED",
                oldValue = "new name",
                newValue = "final name",
                loggedAt = dateTime
            )
        )
    }

    @Test
    fun `should return change logs for target id when history exists`() {
        runTest {
            // Given
            val targetId = "xyz-123"
            val logs = sampleLogEntries()
            coEvery { logRepository.getLogByTargetId(targetId) } returns logs

            // When
            val result = getChangeLogEntriesForTargetIdUseCase.getFormattedLog(targetId)

            // Then
            result.shouldContainExactly(
                listOf(
                    "user farah changed project xyz-123 from old name to new name at ${
                        DateTimeFormatter.formatDateTimeForDisplay(
                            logs[0].loggedAt
                        )
                    }", "user heba changed project xyz-123 from new name to final name at ${
                        DateTimeFormatter.formatDateTimeForDisplay(
                            logs[1].loggedAt
                        )
                    }"
                )
            )
        }
    }

    @Test
    fun `should return empty list when no Logs exist for target id`() {
        runTest {
            // Given
            val targetId = "123"
            coEvery { logRepository.getLogByTargetId(targetId) } returns emptyList()

            // When
            val result = getChangeLogEntriesForTargetIdUseCase.getFormattedLog(targetId)

            // Then
            result shouldBe emptyList()
        }
    }
}
