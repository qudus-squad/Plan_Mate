package org.qudus.squad.model

class AdminUser(
    username: String,
    passwordHash: String,
) : User(username, passwordHash) {
    override val role = UserRole.ADMIN
}