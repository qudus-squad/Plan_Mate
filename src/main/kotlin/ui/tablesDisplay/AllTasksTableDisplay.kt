package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.Task
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class AllTasksTableDisplay (
    private val dateFormater: DateTimeFormatter
){
        fun invoke(tasks: List<Task>) {
            val idColumnWith = 10
            val nameColumnWidth = 20
            val dateColumnWidth = 20
            val bordersWidth = 2
            val totalWidth = idColumnWith + nameColumnWidth + dateColumnWidth + bordersWidth
            val topHeaderWidth = totalWidth / 2 - 7

            println("Ξ".repeat(topHeaderWidth)
                    + "   PLAN MATE   "
                    + "Ξ".repeat(topHeaderWidth))
            println("┌${"─".repeat(totalWidth)}┐")
            println("│${"FIND TASK".center(totalWidth)}│")
            println("│${"─".repeat(totalWidth)}│")
            println("│${"ID".center(idColumnWith )}" +
                    "│${"NAME".center(nameColumnWidth )}" +
                    "│${"DATE CREATED".center(dateColumnWidth )}│")
            println("│${"─".repeat(totalWidth)}│")

            tasks.forEachIndexed { index, task ->
                val id = index + 1
                val formatedDate = dateFormater.formatDateTimeForDisplay(task.createdAt)
                println("│${id.toString().center(idColumnWith )}" +
                        "│${task.title.center(nameColumnWidth )}" +
                        "│${formatedDate.center(dateColumnWidth )}│") }
            println("└${"─".repeat(totalWidth)}┘")
        }
    }
