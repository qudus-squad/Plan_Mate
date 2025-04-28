package org.qudus.squad.model

abstract class User(
    val id: String,
    val username: String,
    val passwordHash: String
) {
    abstract val role: UserRole
}

class AdminUser(
    id: String,
    username: String,
    passwordHash: String
) : User(id, username, passwordHash) {
    override val role = UserRole.ADMIN
}

class MateUser(
    id: String,
    username: String,
    passwordHash: String
) : User(id, username, passwordHash) {
    override val role = UserRole.MATE
}

enum class UserRole {
    ADMIN,
    MATE
}