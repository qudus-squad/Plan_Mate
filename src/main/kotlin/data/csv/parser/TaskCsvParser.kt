package org.qudus.squad.data.csv.parser

import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.Task

class TaskCsvParser : CsvParser<Task> {

    override fun fromCsvRow(row: String): Task {
        val cleanedLine = row.trim().replace("\"", "")
        val taskList = cleanedLine.split(',')
        return Task(
            id = taskList[TaskCsvColumnIndex.ID.index],
            title = taskList[TaskCsvColumnIndex.TITLE.index],
            description = taskList[TaskCsvColumnIndex.DESCRIPTION.index],
            projectId = taskList[TaskCsvColumnIndex.PROJECT_ID.index],
            taskState = TaskState(taskList[TaskCsvColumnIndex.STATE_ID.index], taskList[TaskCsvColumnIndex.STATE_NAME.index]),
            creatorUserID = taskList[TaskCsvColumnIndex.CREATOR_USER_ID.index],
            createdAt = taskList[TaskCsvColumnIndex.CREATED_AT.index].toLocalDateTime(),
            lastUpdatedAt = taskList[TaskCsvColumnIndex.LAST_UPDATED_AT.index].toLocalDateTime(),
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