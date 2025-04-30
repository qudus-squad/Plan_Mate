package org.qudus.squad.model

import org.qudus.squad.logic.GenerateUUID

abstract class User(
    val username: String,
    val passwordHash: String,
) {
    val userId: String = GenerateUUID().generate()
    abstract val role: UserRole
}

enum class UserRole {
    ADMIN,
    MATE
}