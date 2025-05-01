package org.qudus.squad.logic.useCases.taskState

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.model.exceptions.UnauthorizedAccessException

class EditTaskStateUseCase(private val stateRepository: StateRepository) {

    fun editState(
        user: User,
        projectId: String,
        oldTaskState: TaskState,
        modifiedTaskState: TaskState,
    ): Result<Unit> {
        if (user.role != UserRole.ADMIN) throw UnauthorizedAccessException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)

        return stateRepository.editTaskState(
            projectId = projectId,
            oldTaskState = oldTaskState,
            modifiedTaskState = modifiedTaskState,
        )
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}