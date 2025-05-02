package org.qudus.squad.data.data_source.task_data_source

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.TaskCsvParser
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class CsvTaskDataSource(
    private val csvReader: CsvReader, private val taskCsvParser: TaskCsvParser
) : TaskDataSource {
    override fun createNewTask(task: Task) {
        val csvLine = taskCsvParser.toCsvRow(task) + "\n"
        FileSystem.SYSTEM.appendingSink(TASKS_FILE.toPath()).buffer().use { sink ->
            sink.writeUtf8(csvLine)
        }
    }

    override fun editExistingTask(updatedTask: Task) {
        val updatedLines = getAllTasks().map {
            if (it.id == updatedTask.id) taskCsvParser.toCsvRow(updatedTask)
            else taskCsvParser.toCsvRow(it)
        }

        FileSystem.SYSTEM.write(TASKS_FILE.toPath()) {
            updatedLines.forEach { line -> writeUtf8(line + "\n") }
        }
    }


    override fun switchTaskState(taskId: String, newTaskState: TaskState) {
        val updatedLines = getAllTasks().map {
            if (it.id == taskId) {
                val updatedTask = it.copy(
                    taskState = newTaskState,
                    lastUpdatedAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
                taskCsvParser.toCsvRow(updatedTask)
            } else {
                taskCsvParser.toCsvRow(it)
            }
        }

        FileSystem.SYSTEM.write(TASKS_FILE.toPath()) {
            updatedLines.forEach { line -> writeUtf8(line + "\n") }
        }
    }

    override fun deleteTaskById(id: String) {
        val remainingTasks = getAllTasks().filter { it.id != id }
        FileSystem.SYSTEM.write(TASKS_FILE.toPath()) {
            remainingTasks.forEach { task -> writeUtf8(taskCsvParser.toCsvRow(task) + "\n") }
        }
    }


    override fun getAllTasksByProjectId(id: String): List<Task> {
        return getAllTasks().filter { it.projectId == id }
    }

    override fun getTaskById(id: String): Task {
        return getAllTasks().first { task -> task.id == id }
    }


    override fun assignTaskToUser(taskId: String, userId: String) {
        val updatedTasks = getAllTasks().map { task ->
            if (task.id == taskId) {
                task.copy(
                    assignedUserId = userId,
                    lastUpdatedAt = kotlinx.datetime.Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                )
            } else task
        }
        FileSystem.SYSTEM.write(TASKS_FILE.toPath()) {
            updatedTasks.forEach { task ->
                writeUtf8(taskCsvParser.toCsvRow(task) + "\n")
            }
        }
    }


    override fun unAssignTask(taskId: String) {
        val updatedTasks = getAllTasks().map { task ->
            if (task.id == taskId) {
                task.copy(
                    assignedUserId = null,
                    lastUpdatedAt = kotlinx.datetime.Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                )
            } else task
        }

        FileSystem.SYSTEM.write(TASKS_FILE.toPath()) {
            updatedTasks.forEach { task ->
                writeUtf8(taskCsvParser.toCsvRow(task) + "\n")
            }
        }
    }


    private fun getAllTasks(): List<Task> {
        return csvReader.read(TASKS_FILE).mapNotNull { line ->
            try {
                taskCsvParser.fromCsvRow(line)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    companion object {
        const val TASKS_FILE = "tasks.csv"
    }
}