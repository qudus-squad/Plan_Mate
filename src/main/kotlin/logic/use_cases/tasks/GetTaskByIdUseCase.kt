package org.qudus.squad.logic.use_cases.tasks

import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.Task

class GetTaskByIdUseCase(
    private val taskRepository: TaskRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase
) {
    suspend fun getTaskById(taskId: String): Task {
        taskDataValidationUseCase.validateTaskId(taskId)
        return taskRepository.getTaskById(taskId)
    }
}