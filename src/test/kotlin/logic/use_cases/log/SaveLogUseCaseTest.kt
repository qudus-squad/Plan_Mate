package logic.use_cases.log

import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType

class SaveLogUseCaseTest{

  private lateinit var logRepository: LogRepository
  private lateinit var saveLogUseCase: SaveLogUseCase

  @BeforeEach
  fun setup() {
   logRepository = mockk(relaxed = true)
   saveLogUseCase = SaveLogUseCase(logRepository)
  }
 @Test
 fun `should call addLog on repository when saveLog is invoked`() {
  // Given
  val dateTime = LocalDateTime(LocalDate(2025, 5, 1), LocalTime(10, 30))
  val logEntry = LogEntry(
   userName = "farah",
   targetId = "hh-ff1",
   targetType = TargetType.TASK,
   action = "CREATED",
   oldValue = null,
   newValue = "Task 1",
   loggedAt = dateTime
  )

  // When
  saveLogUseCase.saveLog(logEntry)

  // Then
  verify {
   logRepository.addNewLog(logEntry)
  }
 }}