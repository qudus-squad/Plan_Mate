package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.utils.StringAlignment.center

class OneProjectTableDisplay() {

    fun displayProjectDetail(project: Project) {

        val taskColumnWidth = 20
        val numberOfStatesHeader = project.taskState.size
        val stateHeaders = project.taskState.map { it.name.padEnd(taskColumnWidth) }
        val bordersSize = 4
        val totalWidth = taskColumnWidth * numberOfStatesHeader + bordersSize
        val topHeaderWidth = totalWidth / 2 - 7
        val toGroupTasksByOneState = project.tasks.groupBy { it.taskState.name }
        val maxRowsInTable = toGroupTasksByOneState.values.maxOfOrNull { it.size } ?: 0

        println("Ξ".repeat(topHeaderWidth)
                + "   PLAN MATE   "
                + "Ξ".repeat(topHeaderWidth))
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${project.title.center(totalWidth)}│")
        println("│${project.description.center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println("│${stateHeaders.joinToString("│").center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")

        for (eachTask in 0 until maxRowsInTable) {
            val taskBuilder = StringBuilder("│")

            for (state in project.taskState) {
                val tasksOfSingleState = toGroupTasksByOneState[state.name] ?: emptyList()
                val task = if (eachTask < tasksOfSingleState.size) "▫${tasksOfSingleState[eachTask].title}" else ""
                taskBuilder.append(task.padEnd(taskColumnWidth) + "│")
            }
            println(taskBuilder.toString())
        }
        println("└${"─".repeat(totalWidth)}┘")
    }
}
fun main() {

    val tasks = listOf(
        Task(title = "Design ", description = "", projectId = "1", taskState = TaskState(name = "IN PROGRESS")),
        Task(title = "Create ", description = "", projectId = "1", taskState = TaskState(name = "IN PROGRESS")),
        Task(title = "Implement ", description = "", projectId = "1", taskState = TaskState(name = "IN PROGRESS")),
        Task(title = "tests", description = "", projectId = "1", taskState = TaskState(name = "DONE")),
        Task(title = "apply", description = "", projectId = "1",taskState = TaskState(name = "DONE")),
        Task(title = "save", description = "", projectId = "1", taskState = TaskState(name = "TODO")),
        Task(title = "Review", description = "", projectId = "1",taskState = TaskState(name = "TODO"))
    )
    val state = listOf(
        TaskState(name = "TODO"),TaskState(name = "DONE" ),TaskState(name = "IN PROGRESS"),TaskState(name = "DNE" )
        ,TaskState(name = "DON" ))

    val project = Project(
        title = "Plan Mate App",
        description = "A project management application",
        tasks = tasks,
        taskState =state
    )
     OneProjectTableDisplay().displayProjectDetail(project)
}