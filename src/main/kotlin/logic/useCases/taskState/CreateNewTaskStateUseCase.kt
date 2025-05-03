package org.qudus.squad.logic.useCases.taskState

import org.qudus.squad.logic.exceptions.UnauthorizedAccessException
import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.model.entity.TaskState
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class CreateNewTaskStateUseCase(private val stateRepository: StateRepository) {

    fun createState(
        user: User,
        projectId: String,
        taskState: TaskState,
    ): Result<Unit> {
        if (user.role != UserRole.ADMIN ) throw UnauthorizedAccessException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)

        return stateRepository.addTaskStateToProject(
            projectId = projectId,
            taskState = taskState,
        )
    }
    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."
    }
}
