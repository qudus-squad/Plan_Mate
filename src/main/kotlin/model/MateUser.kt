package org.qudus.squad.model


class MateUser(
    username: String,
    passwordHash: String
) : User(username, passwordHash) {
    override val role = UserRole.MATE
}