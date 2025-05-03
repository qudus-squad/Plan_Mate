package logic.useCases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.NoChangeHistoryFoundException
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.useCases.log.GetLogByTargetIdUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.utils.DateTimeFormatter

class GetLogByTargetIdUseCaseTest {

    private lateinit var logRepository: LogRepository
    private lateinit var getLogByTargetIdUseCase: GetLogByTargetIdUseCase

    @BeforeEach
    fun setUp() {
        logRepository = mockk()
        getLogByTargetIdUseCase = GetLogByTargetIdUseCase(logRepository)
    }

    private fun sampleLogEntries(): List<LogEntry> {
        val dateTime = LocalDateTime(
            date = LocalDate(2025, 4, 30),
            time = LocalTime(2, 45)
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
            ),
            LogEntry(
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
    fun `should return list of change Logs when target id has change history`() {
        // Given
        val targetId = "xyz-123"
        val logs = sampleLogEntries()
        every { logRepository.getLogByTargetId(targetId) } returns logs

        // When
        val result = getLogByTargetIdUseCase.getFormattedLog(targetId)

        // Then
        result.shouldContainExactly(
            listOf(
                "user farah changed project xyz-123 from old name to new name at ${
                    DateTimeFormatter.formatDateTimeForDisplay(
                        logs[0].loggedAt
                    )
                }",
                "user heba changed project xyz-123 from new name to final name at ${
                    DateTimeFormatter.formatDateTimeForDisplay(
                        logs[1].loggedAt
                    )
                }"
            )
        )
    }

    @Test
    fun `should return empty list when no change logs found for the given target ID`() {
        // Given
        val targetId = "123"
        every { logRepository.getLogByTargetId(targetId) } returns emptyList()

        // When
        val result = getLogByTargetIdUseCase.getFormattedLog(targetId)

        // Then
        result shouldBe emptyList()
    }

    @Test
    fun `should throw NoChangeHistoryFoundException when no logs found`() {
        // Given
        val targetId = "not-found"
        every { logRepository.getLogByTargetId(targetId) } returns null

        // When & Then
        val exception = shouldThrow<NoChangeHistoryFoundException> {
            getLogByTargetIdUseCase.getFormattedLog(targetId)
        }
        exception.message shouldBe "No change history found for ID: not-found"
    }
}
