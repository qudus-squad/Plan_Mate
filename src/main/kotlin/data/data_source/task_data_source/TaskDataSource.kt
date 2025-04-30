package org.qudus.squad.data.data_source.task_data_source

import org.qudus.squad.model.Task

interface TaskDataSource {
    fun getAllData() : List<Task>
}