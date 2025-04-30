package org.qudus.squad.logic.usecases.state

import org.qudus.squad.model.exceptions.UnauthorizedAccessException
import org.qudus.squad.logic.repositories.state_repository.StateRepository
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.State
import org.qudus.squad.model.User

class CreateStateUseCase(private val stateRepository: StateRepository) {

    fun createState(
        user: User,
        projectId: String,
        state: State,
    ): Result<Unit> {
        if (user !is AdminUser) return Result.failure(UnauthorizedAccessException())

        return stateRepository.addStateToProject(
            projectId = projectId,
            state = state,
        )
    }
}