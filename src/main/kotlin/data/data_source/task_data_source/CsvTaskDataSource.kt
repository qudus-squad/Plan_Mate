package org.qudus.squad.data.data_source.task_data_source
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
        val csvLine = taskCsvParser.toCsvRow(task)

        FileSystem.SYSTEM.appendingSink(TASKS_FILE.toPath()).buffer().use { sink ->
            sink.writeUtf8(csvLine + "\n")
        }
    }

    override fun editExistingTask(updatedTask: Task) {
        TODO("Not yet implemented")
    }

    override fun switchTaskState(taskId: String, newTaskState: TaskState) {
        TODO("Not yet implemented")
    }

    override fun deleteTaskById(id: String) {
        val tasks = getAllTasksByProjectId().filterNot { it.id == id }

        FileSystem.SYSTEM.sink(TASKS_FILE.toPath()).buffer().use { sink ->
            tasks.forEach { task ->
                val csvLine = taskCsvParser.toCsvRow(task)
                sink.writeUtf8(csvLine + "\n")
            }
        }
    }

    override fun getAllTasksByProjectId(): List<Task> {
        return csvReader.read(TASKS_FILE).mapNotNull { line ->
            try {
                taskCsvParser.fromCsvRow(line)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    override fun getTaskById(id: String): Task {
        return getAllTasksByProjectId().first { task -> task.id == id }
    }

    override fun assignTaskToUser(taskId: String, userId: String) {
        TODO("Not yet implemented")
    }

    override fun unAssignTask(taskId: String) {
        TODO("Not yet implemented")
    }

    companion object {
        const val TASKS_FILE = "tasks.csv"
    }
}