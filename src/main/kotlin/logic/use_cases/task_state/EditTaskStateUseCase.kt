package logic.use_cases.task_state

import org.qudus.squad.logic.repositories.TaskStateRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.TaskState

class EditTaskStateUseCase(
    private val taskStateRepository: TaskStateRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase
) {
    suspend fun editTaskState(taskState: TaskState): Boolean {
        taskDataValidationUseCase.validateTaskStateName(taskState.name)
        return taskStateRepository.editTaskState(taskState)
    }
} 