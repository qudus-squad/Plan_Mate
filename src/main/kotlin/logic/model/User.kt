package org.qudus.squad.logic.model

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
