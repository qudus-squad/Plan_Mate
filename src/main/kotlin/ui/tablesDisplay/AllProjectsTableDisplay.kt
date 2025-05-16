package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.Project
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class AllProjectsTableDisplay(
    private val dateFormater: DateTimeFormatter
) {
    fun invoke(projects: List<Project>) {
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
        println("│${"ALL PROJECT".center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println("│${"ID".center(idColumnWith )}" +
                "│${"NAME".center(nameColumnWidth )}" +
                "│${"DATE CREATED".center(dateColumnWidth )}│")
        println("│${"─".repeat(totalWidth)}│")

        projects.forEachIndexed { index, project ->
            val id = index + 1
            val formatedDate = dateFormater.formatDateTimeForDisplay(project.createdAt)
            println("│${id.toString().center(idColumnWith )}" +
                    "│${project.title.center(nameColumnWidth )}" +
                    "│${formatedDate.center(dateColumnWidth )}│") }
        println("└${"─".repeat(totalWidth)}┘")
    }
}