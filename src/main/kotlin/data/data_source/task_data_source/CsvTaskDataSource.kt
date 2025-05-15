package org.qudus.squad.data.data_source.task_data_source

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.TaskCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState

class CsvTaskDataSource(
    private val csvReader: CsvReader,
    private val taskCsvParser: TaskCsvParser,
    private val writeInFileUseCase: WriteInFileUseCase
) : TaskDataSource {

    override suspend fun createNewTask(task: Task): Task {
        return try {
            val newTaskCsvRow = taskCsvParser.toCsvRow(task) + "\n"
            writeInFileUseCase.writeLineToFile(TASKS_FILE, newTaskCsvRow)
            task
        } catch (e: Exception) {
            throw InvalidToAddTaskException()
        }

    }

    override suspend fun editExistingTask(updatedTask: Task): Boolean {
        return try {
            val taskCsvLines = getAllTasks().map { task ->
                if (task.id == updatedTask.id) taskCsvParser.toCsvRow(updatedTask)
                else taskCsvParser.toCsvRow(task)
            }
            writeInFileUseCase.writeLinesToFile(TASKS_FILE, taskCsvLines)
            true
        } catch (e: Exception) {
            throw InvalidToEditTaskException()
        }
    }

    override suspend fun switchTaskState(taskId: String, newTaskState: TaskState): Boolean {
        return try {
            val taskCsvLines = getAllTasks().map { task ->
                if (task.id == taskId) {
                    val updatedTask = task.copy(
                        taskState = newTaskState,
                        lastUpdatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                    taskCsvParser.toCsvRow(updatedTask)
                } else {
                    taskCsvParser.toCsvRow(task)
                }
            }
            writeInFileUseCase.writeLinesToFile(TASKS_FILE, taskCsvLines)
            true
        } catch (e: Exception) {
            throw InvalidToEditTaskException()
        }
    }

    override suspend fun deleteTaskById(id: String): Boolean {
        return try {
            val filteredTasks = getAllTasks().filter { it.id != id }
            val csvLines = filteredTasks.map { project -> taskCsvParser.toCsvRow(project) }
            writeInFileUseCase.writeLinesToFile(TASKS_FILE, csvLines)
            true
        } catch (e: Exception) {
            throw InvalidToDeleteTaskException()
        }
    }

    override suspend fun getAllTasksByProjectId(id: String): List<Task> {
        return try {
            getAllTasks().filter { it.projectId == id }
        } catch (e: Exception) {
            throw InvalidToGetAllTasksException()
        }
    }

    override suspend fun getTaskById(id: String): Task {
        return getAllTasks().firstOrNull { it.id == id } ?: throw InvalidToGetTaskByIdTaskException(

        )
    }

    override suspend fun assignTaskToUser(taskId: String, userId: String): Boolean {
        return try {
            val updatedTaskList = getAllTasks().map { task ->
                if (task.id == taskId) {
                    task.copy(
                        assignedUserId = userId,
                        lastUpdatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                } else task
            }
            val csvLines = updatedTaskList.map { task -> taskCsvParser.toCsvRow(task) }
            writeInFileUseCase.writeLinesToFile(TASKS_FILE, csvLines)
            true
        } catch (e: Exception) {
            throw InvalidToEditTaskException()
        }
    }

    override suspend fun unAssignTask(taskId: String): Boolean {
        return try {
            val updatedTaskList = getAllTasks().map { task ->
                if (task.id == taskId) {
                    task.copy(
                        assignedUserId = null,
                        lastUpdatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                } else task
            }
            val csvLines = updatedTaskList.map { task -> taskCsvParser.toCsvRow(task) }
            writeInFileUseCase.writeLinesToFile(TASKS_FILE, csvLines)
            true
        } catch (e: Exception) {
            throw InvalidToEditTaskException()
        }
    }

    override suspend fun getAllTasks(): List<Task> {
        return csvReader.read(TASKS_FILE).mapNotNull { csvRow ->
            try {
                taskCsvParser.fromCsvRow(csvRow)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }


    companion object {
        const val TASKS_FILE = "tasks.csv"
    }
}