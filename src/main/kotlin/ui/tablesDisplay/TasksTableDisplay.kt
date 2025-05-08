package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class TasksTableDisplay (
    private val dateFormater: DateTimeFormatter
){

        fun displayTasksTable(tasks: List<Task>) {
            val idColumnWith = 36
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

            tasks.forEach { task ->
                val formatedDate = dateFormater.formatDateTimeForDisplay(task.createdAt)
                println("│${task.id.center(idColumnWith )}" +
                        "│${task.title.center(nameColumnWidth )}" +
                        "│${formatedDate.center(dateColumnWidth )}│") }
            println("└${"─".repeat(totalWidth)}┘")
        }
    }
fun main() {
    val projects: List<Task> = listOf(
        Task(
            id = "1",
            title = "task 1",
            description = "///",
            projectId = "proj1",
            taskState = TaskState(name = ""),
        ),
        Task(
            id = "2",
            title = "task 2",
            description = "///",
            projectId = "proj2",
            taskState = TaskState(name = ""),
        )
    )

    TasksTableDisplay(dateFormater = DateTimeFormatter).displayTasksTable(projects)
}
