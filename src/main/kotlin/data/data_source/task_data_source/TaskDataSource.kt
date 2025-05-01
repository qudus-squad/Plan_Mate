package org.qudus.squad.data.data_source.task_data_source

import org.qudus.squad.model.State
import org.qudus.squad.model.Task

interface TaskDataSource {
    fun getAllTasks(): List<Task>
    fun getTaskById(id: String): Task
    fun deleteTaskById(id: String)
    fun addTask(task: Task)
    }