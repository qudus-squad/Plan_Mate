package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.utils.StringAlignment.center

class AllStatesTableDisplay {
    fun invoke(tasks: List<TaskState>) {
        val idColumnWith = 10
        val nameColumnWidth = 20
        val bordersWidth = 1
        val totalWidth = idColumnWith + nameColumnWidth + bordersWidth
        val topHeaderWidth = totalWidth / 4

        println("Ξ".repeat(topHeaderWidth)
                + "   PLAN MATE   "
                + "Ξ".repeat(topHeaderWidth))
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${"Find States".center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println("│${"ID".center(idColumnWith)}│" +
                "${"NAME".center(nameColumnWidth)}│")
        println("│${"─".repeat(totalWidth)}│")

        tasks.forEachIndexed { index, taskState ->
            val id = index +1
            println("│${id.toString().center(idColumnWith)}│" +
                    "${taskState.name.center(nameColumnWidth)}│")
        }
        println("└${"─".repeat(totalWidth)}┘")
    }
}

