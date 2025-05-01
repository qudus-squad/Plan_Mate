package org.qudus.squad.logic.useCases.taskState

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.model.exceptions.UnauthorizedAccessException

class DeleteTaskStateUseCase(private val stateRepository: StateRepository) {

    fun deleteState(
        user: User,
        projectId: String,
        taskState: TaskState,
    ): Result<Unit> {
        if (user.role != UserRole.ADMIN) throw UnauthorizedAccessException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
        return stateRepository.deleteTaskState(
            projectId = projectId,
            taskState = taskState,
        )
    }
    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}