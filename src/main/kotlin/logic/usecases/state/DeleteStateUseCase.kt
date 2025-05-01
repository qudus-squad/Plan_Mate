package org.qudus.squad.logic.usecases.state

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.State
import org.qudus.squad.model.User
import org.qudus.squad.model.exceptions.UnauthorizedAccessException

class DeleteStateUseCase(private val stateRepository: StateRepository) {

    fun deleteState(
        user: User,
        projectId: String,
        state: State,
    ): Result<Unit> {
        if (user !is AdminUser) throw UnauthorizedAccessException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
        return stateRepository.deleteState(
            projectId = projectId,
            state = state,
        )
    }
    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}