package logic.use_cases.log

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.BeforeEach
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import kotlin.test.Test

class GetAllLogsUseCaseTest{
    private lateinit var logRepository: LogRepository
    private lateinit var getAllLogsUseCase: GetAllLogsUseCase

    @BeforeEach
    fun setUp() {
        logRepository = mockk(relaxed = true)
        getAllLogsUseCase = GetAllLogsUseCase(logRepository)
    }

    private fun sampleLogEntries(): List<LogEntry> {
        val dateTime = LocalDateTime(LocalDate(2025, 5, 1), LocalTime(10, 30))
        return listOf(
            LogEntry(
                userName = "farah",
                targetId = "hh-ff1",
                targetType = TargetType.TASK,
                action = "CREATED",
                oldValue = null,
                newValue = "Task 1",
                loggedAt = dateTime
            ),
            LogEntry(
                userName = "heba",
                targetId = "frg-g2",
                targetType = TargetType.PROJECT,
                action = "DELETED",
                oldValue = "Project X",
                newValue = null,
                loggedAt = dateTime
            )
        )
    }

    @Test
    fun `should return all logs when repository has logs`() {
        // Given
        val logs = sampleLogEntries()
        every { logRepository.getAllLogs() } returns logs

        // When
        val result = getAllLogsUseCase.getAllLogsUseCas()

        // Then
        result.shouldContainExactly(logs)
    }

    @Test
    fun `should return empty list when repository has no logs`() {
        // Given
        every { logRepository.getAllLogs() } returns emptyList()

        // When
        val result = getAllLogsUseCase.getAllLogsUseCas()

        // Then
        result shouldBe emptyList()
    }
}