package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.utils.StringAlignment

class TaskStateDisplay {
    fun displayStatesTable(tasks: List<TaskState>) {
        val idColumnWith = 36
        val nameColumnWidth = 20
        val bordersWidth = 2
        val totalWidth = idColumnWith + nameColumnWidth + bordersWidth
        val topHeaderWidth = totalWidth / 2 - 7

        println("Ξ".repeat(topHeaderWidth)
                + "   PLAN MATE   "
                + "Ξ".repeat(topHeaderWidth))
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${"Find States".center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println("│${"ID".center(idColumnWith)}│" +
                "${"NAME".center(nameColumnWidth)}│")
        println("│${"─".repeat(totalWidth)}│")

        tasks.forEach { task ->
            println("│${task.id.center(idColumnWith)}│" +
                    "${task.name.center(nameColumnWidth)}│")
        }

        println("└${"─".repeat(totalWidth)}┘")
    }

    private fun String.center(width: Int): String = StringAlignment.run { this@center.center(width) }
}

// Main function to test it
fun main() {
    val display = TaskStateDisplay()

    val tasks = listOf(
        TaskState("8c940a21-1234-4c8e-8750-51d59a123456", "Design UI"),
        TaskState("23f9af33-5678-4f21-994b-6bbcd45e89de", "Write Tests"),
        TaskState("a1923ab9-6789-43cf-87c2-9e8b1fcd093a", "Fix Bugs")
    )

    display.displayStatesTable(tasks)
}
