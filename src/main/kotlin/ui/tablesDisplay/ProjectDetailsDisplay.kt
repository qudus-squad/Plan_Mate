package org.qudus.squad.ui.tablesDisplay

import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.ui.utils.StringAlignment.center

class ProjectDetailsDisplay() {
    suspend fun invoke(project: Project) {
        val repo: TaskRepository = getKoin().get()

        val taskColumnWidth = 20
        val numberOfStatesHeader = project.taskState.size
        val bordersSize = numberOfStatesHeader + 1
        val totalWidth = taskColumnWidth * numberOfStatesHeader + bordersSize
        val topHeaderWidth = totalWidth / 2 - 7 // Adjusted for "PLAN MATE" text

        val toGroupTasksByOneState = project.tasks.groupBy { it.taskState.name }
        val maxRowsInTable = toGroupTasksByOneState.values.maxOfOrNull { it.size } ?: 0

        println("Ξ".repeat(topHeaderWidth) + "   PLAN MATE   " + "Ξ".repeat(topHeaderWidth))
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${project.title.center(totalWidth)}│")
        println("│${project.description.center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")

        val headerBuilder = StringBuilder("│")
        project.taskState.forEach { state ->
            headerBuilder.append(state.name.padEnd(taskColumnWidth).take(taskColumnWidth)).append("│")
        }
        println(headerBuilder.toString())
        println("│${"─".repeat(totalWidth)}│")

        // Tasks
        for (row in 0 until maxRowsInTable) {
            val taskBuilder = StringBuilder("│")
            for (state in project.taskState) {
                val tasks = toGroupTasksByOneState[state.name] ?: emptyList()
                val task = if (row < tasks.size) "▫${tasks[row].title}" else ""
                taskBuilder.append(task.padEnd(taskColumnWidth)).append("│")
            }
            println(taskBuilder.toString())
        }

        println("└${"─".repeat(totalWidth)}┘")
    }
}

suspend fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }

    val tasks = listOf(
        Task(title = "Design", description = "", projectId = "1", creatorUserID = "", taskState = TaskState(name = "IN PROGRESS")),
        Task(title = "Create", description = "", projectId = "1", creatorUserID = "", taskState = TaskState(name = "IN PROGRESS")),
        Task(title = "Implement", description = "", projectId = "1", creatorUserID = "", taskState = TaskState(name = "IN PROGRESS")),
        Task(title = "tests", description = "", projectId = "1", creatorUserID = "", taskState = TaskState(name = "DONE")),
        Task(title = "apply", description = "", projectId = "1", creatorUserID = "", taskState = TaskState(name = "DONE")),
        Task(title = "save", description = "", projectId = "1", creatorUserID = "", taskState = TaskState(name = "TODO")),
        Task(title = "Review", description = "", projectId = "1", creatorUserID = "", taskState = TaskState(name = "TOD"))
    )

    val state = listOf(
        TaskState(name = "TODO"),
        TaskState(name = "DONE"),
        TaskState(name = "IN PROGRESS"),
        TaskState(name = "DNE"),
        TaskState(name = "DON")
    )

    val project = Project(
        id = "1",
        title = "Plan Mate App",
        description = "A project management application",
        tasks = tasks,
        creatorUserId = "",
        taskState = state
    )

    ProjectDetailsDisplay().invoke(project)
}