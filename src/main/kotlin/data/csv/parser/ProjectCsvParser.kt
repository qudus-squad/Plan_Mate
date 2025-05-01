package org.qudus.squad.data.csv.parser

import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.model.entity.Project
import org.qudus.squad.model.entity.TaskState


class ProjectCsvParser : CsvParser<Project> {
    override fun fromCsvRow(row: String): Project {
        val projectList = row.smartCsvParser()
        return Project(
            id = projectList[ProjectCsvColumnIndex.ID.index],
            title = projectList[ProjectCsvColumnIndex.TITLE.index],
            description = projectList[ProjectCsvColumnIndex.DESCRIPTION.index],
            creatorUserId = projectList[ProjectCsvColumnIndex.CREATOR_USER_ID.index],
            createdAt = projectList[ProjectCsvColumnIndex.CREATED_AT.index].toLocalDateTime(),
            lastUpdateAt = projectList[ProjectCsvColumnIndex.LAST_UPDATED_AT.index].toLocalDateTime(),
            taskState = projectList[ProjectCsvColumnIndex.STATE.index].parseStateList()
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

    fun String.parseStateList(): List<TaskState> {
        return this.removePrefix("[").removeSuffix("]").split(',').map { it.trim() }.filter { it.isNotEmpty() }
            .map { name -> TaskState(name = name) }
    }

    override fun toCsvRow(model: Project): String {
        return listOf(
            model.id,
            model.title,
            model.description,
            model.creatorUserId,
            model.createdAt,
            model.lastUpdateAt,
            model.taskState.toString()
        ).joinToString(",")
    }
}