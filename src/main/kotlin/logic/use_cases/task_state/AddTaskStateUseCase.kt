package logic.use_cases.task_state

import org.qudus.squad.logic.repositories.TaskStateRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.TaskState

class AddTaskStateUseCase(
    private val taskStateRepository: TaskStateRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase
) {
    suspend fun addTaskState(taskState: TaskState): TaskState {
        taskDataValidationUseCase.validateTaskStateName(taskState.name)
        return taskStateRepository.addNewTaskState(taskState)
    }
}