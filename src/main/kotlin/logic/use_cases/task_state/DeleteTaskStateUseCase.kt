package logic.use_cases.task_state

import org.qudus.squad.logic.repositories.TaskStateRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase

class DeleteTaskStateUseCase(
    private val taskStateRepository: TaskStateRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase
) {
    suspend fun deleteTaskState(stateId: String): Boolean {
        taskDataValidationUseCase.validateTaskStateId(stateId)
        return taskStateRepository.deleteTaskStateById(stateId)
    }
} 