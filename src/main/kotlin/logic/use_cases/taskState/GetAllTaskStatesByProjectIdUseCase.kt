package logic.use_cases.taskState

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.entity.TaskState

class GetAllTaskStatesByProjectIdUseCase(private val stateRepository: StateRepository) {

    fun getAllTaskStates(projectId: String): List<TaskState> {

        return stateRepository.getAllTaskStatesByProjectId(projectId = projectId)
    }
}