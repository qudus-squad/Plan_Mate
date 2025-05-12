package org.qudus.squad.data.csv.parser

import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.logic.model.TaskState
import org.qudus.squad.logic.model.Task

class TaskCsvParser : CsvParser<Task> {

    override fun fromCsvRow(row: String): Task {
        val cleanedLine = row.trim().replace("\"", "")
        val taskList = cleanedLine.split(',')
        return Task(
            id = taskList[TaskCsvColumnIndex.ID],
            title = taskList[TaskCsvColumnIndex.TITLE],
            description = taskList[TaskCsvColumnIndex.DESCRIPTION],
            projectId = taskList[TaskCsvColumnIndex.PROJECT_ID],
            taskState = TaskState(taskList[TaskCsvColumnIndex.STATE_ID], taskList[TaskCsvColumnIndex.STATE_NAME]),
            creatorUserID = taskList[TaskCsvColumnIndex.CREATOR_USER_ID],
            createdAt = taskList[TaskCsvColumnIndex.CREATED_AT].toLocalDateTime(),
            lastUpdatedAt = taskList[TaskCsvColumnIndex.LAST_UPDATED_AT].toLocalDateTime(),
        )
    }

    override fun toCsvRow(model: Task): String {
        return listOf(
           model.id,
            model.title,
            model.description,
            model.projectId,
            model.taskState.id,
            model.taskState.name,
            model.creatorUserID,
            model.createdAt.toString(),
            model.lastUpdatedAt.toString()
        ).joinToString(",")
    }
}