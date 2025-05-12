package org.qudus.squad.logic.use_cases.user

import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase

class GetUserByProjectId(
    private val userRepository: UserRepository, private val projectDataValidationUseCase: ProjectDataValidationUseCase
) {
    suspend fun getUserByProjectId(projectId: String){
        projectDataValidationUseCase.validateProjectId(projectId)
        userRepository.getUserByProjectId(projectId)
    }
}