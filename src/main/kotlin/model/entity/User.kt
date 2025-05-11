package org.qudus.squad.model.entity

import logic.exceptions.AccessDeniedException
import org.qudus.squad.ui.utils.GenerateUUID

data class User(
    val username: String,
    val passwordHash: String,
    val userId: String = GenerateUUID().generate(),
    val role: UserRole
)

enum class UserRole {
    ADMIN,
    MATE
}

fun UserRole.checkCurrentRoleIsAdmin() {
    if (this != UserRole.ADMIN) {
        throw AccessDeniedException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
    }
}

fun User.checkUserIsAdmin() {
    if (this.role != UserRole.ADMIN) {
        throw AccessDeniedException(NOT_AUTHORIZED_EXCEPTION_MESSAGE)
    }
}

private const val NOT_AUTHORIZED_EXCEPTION_MESSAGE = "User is not authorized to perform this action."

