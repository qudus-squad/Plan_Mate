package org.qudus.squad.logic.usecases.state

import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.State
import org.qudus.squad.model.User
import org.qudus.squad.model.exceptions.UnauthorizedAccessException

class EditStateUseCase(private val stateRepository: StateRepository) {

    fun editState(
        user: User,
        projectId: String,
        oldState: State,
        modifiedState: State,
    ): Result<Unit> {
        if (user !is AdminUser) throw UnauthorizedAccessException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)

        return stateRepository.editState(
            projectId = projectId,
            oldState = oldState,
            modifiedState = modifiedState,
        )
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}