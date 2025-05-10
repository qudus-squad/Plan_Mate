package org.qudus.squad.data.data_source.user_data_source.remote

import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

data class UserDto(
    val username: String,
    val passwordHash: String,
    val userId: String,
    val role: String
)

fun UserDto.toUser(): User {
    return User(
        username = username,
        passwordHash = passwordHash,
        userId = userId,
        role = when (role) {
            "MATE" -> UserRole.MATE
            "ADMIN" -> UserRole.ADMIN
            else -> UserRole.MATE
        }
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        username = this.username,
        passwordHash = this.passwordHash,
        userId = this.userId,
        role = when(this.role){
            UserRole.ADMIN -> "ADMIN"
            UserRole.MATE -> "MATE"
        }
    )
}
