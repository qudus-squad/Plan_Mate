package org.qudus.squad.data.data_source.task_data_source
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.TaskCsvParser
import org.qudus.squad.model.entity.Task

class CsvTaskDataSource(
    private val csvReader: CsvReader, private val taskCsvParser: TaskCsvParser
) : TaskDataSource {

    override fun getAllTasks(): List<Task> {
        return csvReader.read(TASKS_FILE).mapNotNull { line ->
            try {
                taskCsvParser.fromCsvRow(line)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    override fun getTaskById(id: String): Task {
        return getAllTasks().first { task -> task.id == id }
    }

    override fun deleteTaskById(id: String) {
        val tasks = getAllTasks().filterNot { it.id == id }

        FileSystem.SYSTEM.sink(TASKS_FILE.toPath()).buffer().use { sink ->
            tasks.forEach { task ->
                val csvLine = taskCsvParser.toCsvRow(task)
                sink.writeUtf8(csvLine + "\n")
            }
        }
    }

    override fun createNewTask(task: Task) {
        val csvLine = taskCsvParser.toCsvRow(task)

        FileSystem.SYSTEM.appendingSink(TASKS_FILE.toPath()).buffer().use { sink ->
            sink.writeUtf8(csvLine + "\n")
        }
    }

    companion object {
        const val TASKS_FILE = "tasks.csv"
    }
}