package org.qudus.squad.data.csv.parser

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class ProjectCsvParser : CsvParser<Project> {
    override fun fromCsvRow(row: String): Project {
        val projectList = row.smartCsvParser()
        return Project(
            id = projectList[ProjectCsvColumnIndex.ID],
            title = projectList[ProjectCsvColumnIndex.TITLE],
            description = projectList[ProjectCsvColumnIndex.DESCRIPTION],
            creatorUserId = projectList[ProjectCsvColumnIndex.CREATOR_USER_ID],
            createdAt = projectList[ProjectCsvColumnIndex.CREATED_AT].toLocalDateTime(),
            lastUpdateAt = projectList[ProjectCsvColumnIndex.LAST_UPDATED_AT].toLocalDateTime(),
            tasks = projectList[ProjectCsvColumnIndex.TASKS].parseTasks()
        )
    }

    private fun String.smartCsvParser(): List<String> {
        val result = mutableListOf<String>()
        val currentEntity = StringBuilder()
        var insideBrackets = false
        var insideQuotes = false
        var i = 0

        while (i < this.length) {
            when (val char = this[i]) {
                '"' -> {
                    insideQuotes = !insideQuotes
                    currentEntity.append(char)
                }

                '[' -> {
                    insideBrackets = true
                    currentEntity.append(char)
                }

                ']' -> {
                    insideBrackets = false
                    currentEntity.append(char)
                }

                ',' -> {
                    if (!insideQuotes && !insideBrackets) {
                        result.add(currentEntity.toString().trim())
                        currentEntity.clear()
                    } else {
                        currentEntity.append(char)
                    }
                }

                else -> currentEntity.append(char)
            }
            i++
        }

        if (currentEntity.isNotEmpty()) {
            result.add(currentEntity.toString().trim())
        }
        return result
    }

    private fun String.parseTasks(): List<Task> {

        val tasksString = this.formatTaskToParse() ?: return emptyList()

        val tasks = mutableListOf<Task>()
        var currentTask = StringBuilder()
        var bracketCount = 0

        for (char in tasksString) {
            when (char) {
                '(' -> {
                    bracketCount++
                    currentTask.append(char)
                }

                ')' -> {
                    bracketCount--
                    currentTask.append(char)

                    if (bracketCount == 0 && currentTask.toString().startsWith("Task(")) {
                        tasks.add(parseTaskFromString(currentTask.toString()))
                        currentTask = StringBuilder()
                    }
                }

                ',' -> {
                    if (bracketCount > 0) {
                        currentTask.append(char)
                    }
                }

                else -> currentTask.append(char)
            }
        }

        return tasks
    }

    private fun parseTaskFromString(taskString: String): Task {
        val taskContent = taskString.removePrefix("Task(").removeSuffix(")")
        val properties = extractProperties(taskContent)

        val taskStateStr = properties["taskState"] ?: "TaskState(id=,name=)"
        val taskState = parseTaskState(taskStateStr)

        return Task(
            id = properties["id"] ?: GenerateUUID().generate(),
            title = properties["title"] ?: "",
            description = properties["description"] ?: "",
            projectId = properties["projectId"] ?: "",
            taskState = taskState,
            creatorUserID = properties["creatorUserID"] ?: "",
            assignedUserId = properties["assignedUserId"],
            createdAt = properties["createdAt"]?.toLocalDateTime() ?: Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()),
            lastUpdatedAt = properties["lastUpdatedAt"]?.toLocalDateTime() ?: Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )
    }

    private fun extractProperties(taskContent: String): MutableMap<String, String> {
        val properties = mutableMapOf<String, String>()
        var propertyName = ""
        var propertyValue = StringBuilder()
        var bracketCount = 0
        var parsingName = true

        for (char in taskContent) {
            when {
                char == '=' && parsingName && bracketCount == 0 -> {
                    propertyName = propertyValue.toString().trim()
                    propertyValue = StringBuilder()
                    parsingName = false
                }

                char == ',' && !parsingName && bracketCount == 0 -> {
                    properties[propertyName] = propertyValue.toString().trim()
                    propertyValue = StringBuilder()
                    parsingName = true
                }

                char == '(' -> {
                    bracketCount++
                    propertyValue.append(char)
                }

                char == ')' -> {
                    bracketCount--
                    propertyValue.append(char)
                }

                else -> propertyValue.append(char)
            }
        }

        if (propertyName.isNotEmpty()) {
            properties[propertyName] = propertyValue.toString().trim()
        }

        return properties
    }

    private fun parseTaskState(taskStateStr: String): TaskState {
        val stateContent = taskStateStr.removePrefix("TaskState(").removeSuffix(")")
        val properties = stateContent.split(",")
            .associate {
                val parts = it.split("=", limit = 2)
                if (parts.size == 2) parts[0].trim() to parts[1].trim() else "" to ""
            }

        return TaskState(
            id = properties["id"] ?: GenerateUUID().generate(),
            name = properties["name"] ?: ""
        )
    }

    override fun toCsvRow(model: Project): String {
        return listOf(
            model.id,
            model.title,
            model.description,
            model.creatorUserId,
            model.createdAt,
            model.lastUpdateAt,
            tasksToString(model.tasks)
        ).joinToString(",")
    }

    private fun tasksToString(tasks: List<Task>): String {
        if (tasks.isEmpty()) return "[]"
        return tasks.joinToString(",", "[", "]") { task ->
            "Task(id=${task.id},title=${task.title},description=${task.description}," +
                    "projectId=${task.projectId}" +
                    ",taskState=TaskState(id=${task.taskState.id},name=${task.taskState.name})," +
                    "creatorUserID=${task.creatorUserID},assignedUserId=${task.assignedUserId}," +
                    "createdAt=${task.createdAt},lastUpdatedAt=${task.lastUpdatedAt})"
        }
    }

    private fun String.formatTaskToParse(): String? {
        if (this.isBlank() || this == "[]") return null
        val tasksString = this.removePrefix("[").removeSuffix("]")
        return tasksString.ifBlank { null }
    }
}