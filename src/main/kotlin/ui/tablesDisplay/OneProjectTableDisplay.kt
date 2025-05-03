package org.qudus.squad.ui.tablesDisplay

import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.logic.useCases.tasks.GetTasksByStateUseCase
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class OneProjectTableDisplay (private val getTasksByStateUseCase: GetTasksByStateUseCase){

    data class Task(val id: String, val name: String, val state: String)

    data class ProjectDetail(
        val id: String,
        val name: String,
        val description: String,
        val states: List<String>,
        val tasks: List<org.qudus.squad.model.entity.Task>
    )
    fun displayProjectDetail(project: ProjectDetail ) {

        val size = project.states.size
        val columnWidth = 18
        val totalWidth = columnWidth * size + 4
        println("Ξ".repeat(totalWidth / 2 - 8) + "   PLAN MATE   " + "Ξ".repeat(totalWidth / 2 - 8))
        println("┌" + "─".repeat(totalWidth - 2) + "┐")
        println("│" + project.name.padStart((totalWidth - 2 + project.name.length) / 2).padEnd(totalWidth - 2) + "│")
        println("│" + project.description.padStart((totalWidth - 2 + project.description.length) / 2).padEnd(totalWidth - 2) + "│")
        println("│" + "─".repeat(totalWidth - 2) + "│")
        // State headers
        val stateHeaders = project.states.map { state -> state.toString().padEnd(columnWidth) }
        println("│" + stateHeaders.joinToString("│") + "│")
        println("│" + "─".repeat(totalWidth - 2) + "│")

      val state = project.states.map { state -> state}
      val groupedTasksByState = getTasksByStateUseCase.filterProjectTasksByState(project.id , state.toString())
        for (i in 0 until 10) {
            val row = StringBuilder("│")
            for (state in project.states) {
                val stateTasks = groupedTasksByState
                val task = if (i < groupedTasksByState.size) "▫${stateTasks[i].title}" else ""
                row.append(task.padEnd(columnWidth)).append("│")
            }
            println(row.toString())
        }
            println("└" + "─".repeat(totalWidth - 2) + "┘")
    }

    }
    fun main() {

            startKoin {
                modules(appModule, useCaseModule, uiModule)}

        val sampleProject = OneProjectTableDisplay.ProjectDetail(
            id = "PRJ-001",
            name = "E-Commerce Platform Development",
            description = "Build new online shopping platform with React frontend",
            states = listOf("Backlog", "Design", "Development", "Testing", "Review", "Done"),
            tasks = listOf(
                Task("T1", "Create wireframes", "Design",projectId ="" ,taskState= TaskState(name = "Backlog"),creatorUserID="",assignedUserId=""),
                Task("T2", "Product API",  "Design",projectId ="" ,taskState= TaskState(name = "Development"),creatorUserID="",assignedUserId=""),
                Task("T3", "Cart functionality", "Development",projectId ="" ,taskState= TaskState(name = "Development"),creatorUserID="",assignedUserId=""),
                Task("T4", "User auth", "Development",projectId ="" ,taskState= TaskState(name = "Backlog"),creatorUserID="",assignedUserId=""),
                Task("T5", "Payment gateway", "Backlog",projectId ="" ,taskState= TaskState(name = "Backlog"),creatorUserID="",assignedUserId=""),
                Task("T6", "Mobile responsive", "Design",projectId ="" ,taskState= TaskState(name = "Development"),creatorUserID="",assignedUserId=""),
                Task("T7", "Unit tests", "Testing",projectId ="" ,taskState= TaskState(name = "Review"),creatorUserID="",assignedUserId=""),
                Task("T8", "Performance tests", "Testing",projectId ="" ,taskState= TaskState(name = "Review"),creatorUserID="",assignedUserId=""),
                Task("T9", "Code review", "Review",projectId ="" ,taskState= TaskState(name = "Review"),creatorUserID="",assignedUserId=""),
                Task("T10", "Deployment", "Done",projectId ="" ,taskState= TaskState(name = "Done"),creatorUserID="",assignedUserId=""),
                Task("T11", "Documentation", "Backlog",projectId ="" ,taskState= TaskState(name = "Done"),creatorUserID="",assignedUserId="")
            )
        )
val usecase : GetTasksByStateUseCase = getKoin().get()
        val display = OneProjectTableDisplay(usecase)
        display.displayProjectDetail(sampleProject)
    }