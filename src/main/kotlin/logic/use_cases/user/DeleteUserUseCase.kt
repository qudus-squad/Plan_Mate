package org.qudus.squad.logic.use_cases.user

import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType
import org.qudus.squad.model.entity.User

class DeleteUserUseCase(
    private val userRepository: UserRepository,
    private val logRepository: LogRepository,
    private val userDataValidator: UserDataValidationUseCase

) {
    suspend fun deleteUser(userName: User, userid: String) {
        if (userDataValidator.validateDeleteUserValues(userName.username, userid)) {
            userRepository.deleteUser(userid)
            logRepository.addNewLog(LogEntry(userName.username, userid, TargetType.USER, "$userid User Deleted"))
        }
    }
}