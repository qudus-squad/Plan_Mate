package org.qudus.squad.model

import java.util.UUID

abstract class User(
    val username: String,
    val passwordHash: String,
) {
    val userId: UUID = UUID.randomUUID()
    abstract val role: UserRole
}

enum class UserRole {
    ADMIN,
    MATE
}