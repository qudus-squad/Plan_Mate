package logic.use_cases.tasks

import org.qudus.squad.logic.repositories.TaskStateRepository
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.model.entity.TaskState

class SwitchTaskStateUseCase(
    private val taskStateRepository: TaskStateRepository,
    private val taskDataValidationUseCase: TaskDataValidationUseCase

) {
    suspend fun switchTaskState(taskState: TaskState) : Boolean{
        taskDataValidationUseCase.validateTaskStateName(taskState.name)
       return taskStateRepository.editTaskState(taskState)
    }
}
