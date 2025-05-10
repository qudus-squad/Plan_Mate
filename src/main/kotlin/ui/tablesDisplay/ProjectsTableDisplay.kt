package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.Project
import org.qudus.squad.ui.utils.DateTimeFormatter
import org.qudus.squad.ui.utils.StringAlignment.center

class ProjectsTableDisplay(
    private val dateFormater: DateTimeFormatter
) {

    fun displayProjectsTable(projects: List<Project>) {
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
        println("│${"ALL PROJECT".center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println("│${"ID".center(idColumnWith )}" +
                "│${"NAME".center(nameColumnWidth )}" +
                "│${"DATE CREATED".center(dateColumnWidth )}│")
        println("│${"─".repeat(totalWidth)}│")

        projects.forEach { project ->
            val formatedDate = dateFormater.formatDateTimeForDisplay(project.createdAt)
            println("│${project.id.center(idColumnWith )}" +
                    "│${project.title.center(nameColumnWidth )}" +
                    "│${formatedDate.center(dateColumnWidth )}│") }
        println("└${"─".repeat(totalWidth)}┘")
    }
}
    fun main ( ) {

        val projects: List<Project> = listOf(
            Project(title = "project 1", description = " /// "),
            Project(title = "project 2", description = " ///"),
            Project(title = "project 3", description = " ///"),
            Project(title = "project 4", description = " /// ")
        )
        ProjectsTableDisplay(dateFormater = DateTimeFormatter).displayProjectsTable(projects)
    }