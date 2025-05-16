package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class AllLogsTableDisplay (
    private val dateFormater: DateTimeFormatter,
) {
    fun invoke(logs: List<LogEntry>) {
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