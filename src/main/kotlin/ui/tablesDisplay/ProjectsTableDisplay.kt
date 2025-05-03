package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.Project
import org.qudus.squad.ui.tablesDisplay.ProjectsTableDisplay.Utils.DATE_WIDTH
import org.qudus.squad.ui.tablesDisplay.ProjectsTableDisplay.Utils.ID_WIDTH
import org.qudus.squad.ui.tablesDisplay.ProjectsTableDisplay.Utils.NAME_WIDTH
import org.qudus.squad.ui.tablesDisplay.ProjectsTableDisplay.Utils.TOTAL_WIDTH

import org.qudus.squad.utils.DateTimeFormatter

class ProjectsTableDisplay(
    private val dateFormater: DateTimeFormatter
) {
    fun displayProjectsTable(projects: List<Project>) {

        println(
            "Ξ"
                .repeat(TOTAL_WIDTH - 10) + "   PLAN MATE   " + "Ξ"
                .repeat(TOTAL_WIDTH - 10)
        )
        println(
            "┌" + "─"
                .repeat(TOTAL_WIDTH - 2) + "┐"
        )
        println(
            "│" + "FIND PROJECT".padStart((TOTAL_WIDTH - 2 + "FIND PROJECT".length) / 2)
                .padEnd(TOTAL_WIDTH - 2) + "│"
        )

        println(
            "│" + "─"
                .repeat(TOTAL_WIDTH - 2) + "│"
        )
        println(
            "│" + "ID"
                .padEnd(ID_WIDTH) + "│" + "NAME"
                .padEnd(NAME_WIDTH) + "│" + "DATE CREATED"
                .padEnd(19) + "│"
        )
        projects.forEach { project ->
            val formatedDate = dateFormater.formatDateTimeForDisplay(project.createdAt)
            println(
                "│" + project.id
                    .padEnd(ID_WIDTH) + "│" + project.title
                    .take(NAME_WIDTH - 3)
                    .padEnd(NAME_WIDTH) + "│" + formatedDate
                    .padEnd(DATE_WIDTH) + "│"
            )
        }
        println(
            "└" + "─"
                .repeat(TOTAL_WIDTH - 2) + "┘"
        )
        println(
            "┌" + "─"
                .repeat(TOTAL_WIDTH - 2) + "┐"
        )
        println(
            "│" + "ENTER PROJECT NAME OR ID TO OPEN PROJECT"
                .padStart((TOTAL_WIDTH - 2 + "ENTER PROJECT NAME OR ID TO OPEN PROJECT".length) / 2)
                .padEnd(TOTAL_WIDTH - 2) + "│"
        )
        println(
            "└" + "─"
                .repeat(TOTAL_WIDTH - 2) + "┘"
        )
    }

    object Utils {
        const val ID_WIDTH = 12
        const val NAME_WIDTH = 18
        const val DATE_WIDTH = 15
        const val TOTAL_WIDTH = ID_WIDTH + NAME_WIDTH + DATE_WIDTH + 8
    }
}