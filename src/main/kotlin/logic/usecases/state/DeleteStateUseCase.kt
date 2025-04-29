package org.qudus.squad.logic.usecases.state

import org.qudus.squad.logic.repositories.state_repository.StateRepository
import org.qudus.squad.model.AdminUser
import org.qudus.squad.model.User

class DeleteStateUseCase(private val stateRepository: StateRepository) {

    fun deleteState(
        user: User,
        projectId: String,
        stateName: String,
    ): Result<Unit> {
        if (user !is AdminUser) return Result.failure(Exception(NOT_AUTHORIZED_EXCEPTION_MESSAGE))

        return stateRepository.deleteState(
            projectId = projectId,
            stateName = stateName
        )
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "Not authorized"
    }
}