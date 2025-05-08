package org.qudus.squad.logic.use_cases.tasks

import logic.exceptions.InvalidTaskIdException
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase

class UnAssignTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend fun unAssignTask(taskId: String){
        if (taskId.isBlank()){
            throw InvalidTaskIdException(TaskDataValidationUseCase.INVALID_TASK_ID)
        }
        taskRepository.unAssignTask(taskId)
    }
}