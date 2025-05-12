package org.qudus.squad.logic.validation

import logic.exceptions.AccessDeniedException
import org.qudus.squad.model.entity.UserRole

class UserRoleValidationUseCase {
    fun checkUserRoleIsAdmin(userRole: UserRole,) {
        if (userRole != UserRole.ADMIN) {
            throw AccessDeniedException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
        }
    }

    companion object {
        const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."

    }
}