package org.qudus.squad.logic.usecases.state

import org.qudus.squad.logic.exceptions.UnauthorizedAccessException
import org.qudus.squad.logic.repositories.state_repository.StateRepository
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.State
import org.qudus.squad.model.User

class EditStateUseCase(private val stateRepository: StateRepository) {

    fun editState(
        user: User,
        projectId: String,
        oldState: State,
        newState: State,
    ): Result<Unit> {
        if (user !is AdminUser) return Result.failure(UnauthorizedAccessException())

        return stateRepository.editState(
            projectId = projectId,
            newState = oldState,
            oldState = newState,
        )
    }
}