package org.qudus.squad.model.entity

import org.qudus.squad.logic.utils.GenerateUUID

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