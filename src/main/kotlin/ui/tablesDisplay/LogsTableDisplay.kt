package org.qudus.squad.ui.tablesDisplay

import kotlinx.datetime.LocalDateTime
import org.qudus.squad.logic.model.LogEntry
import org.qudus.squad.logic.model.TargetType
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class LogsTableDisplay (
    private val dateFormater: DateTimeFormatter,
) {
    fun displayLogsDetails(logs: List<LogEntry>) {
         val topHeaderWidth = 55
         val dateColumnWidth = 20
         val userNameColumnWidth = 20
         val actionColumnWidth = 80
         val borderSize = 4
         val totalWidth = dateColumnWidth + actionColumnWidth + userNameColumnWidth + borderSize

        println("Ξ".repeat(topHeaderWidth)
                +"   PLAN MATE   "
                +"Ξ".repeat(topHeaderWidth))
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${"LOGS HISTORY".center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println("│${"DATE".center(dateColumnWidth +2)}" +
                "│${"USERNAME".center(userNameColumnWidth )}" +
                "│${"ACTION".center(actionColumnWidth )}│")
        println("│${"─".repeat(totalWidth)}│")
        logs.forEach { log ->
            val formatedDate = dateFormater.formatDateTimeForDisplay(log.loggedAt)
            println("│ ${formatedDate.center(dateColumnWidth +1)}│"
                    + "${log.userName.center(userNameColumnWidth)}│"
                    +" ${log.action.center(actionColumnWidth - 1)}│")
        }
        println("└${"─".repeat(totalWidth)}┘")
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${"PRESS ' ENTER ' TO EXIT".center(totalWidth)}│")
        println("└${"─".repeat(totalWidth)}┘")

    }
}
fun main() {
    val logs = listOf(
        LogEntry(
            userName = "user1",
            targetId = "123",
            targetType = TargetType.PROJECT,
            action = "DELETED",
            oldValue = "Old Project Name",
            loggedAt = LocalDateTime(200, 12, 17, 10, 0)
        ),
        LogEntry(
            userName = "user2",
            targetId = "456",
            targetType = TargetType.TASK,
            action = "CREATED",
            newValue = "New Task Name",
            loggedAt = LocalDateTime(204, 12, 17, 11, 0)
        ),
        LogEntry(
            userName = "user3",
            targetId = "789",
            targetType = TargetType.TASK,
            action = "EDITED",
            oldValue = "Old Task Name",
            newValue = "New Task Name",
            loggedAt = LocalDateTime(204, 12, 17, 12, 0)
        )
    )
    LogsTableDisplay(
        dateFormater= DateTimeFormatter,
    ).displayLogsDetails(logs)
}