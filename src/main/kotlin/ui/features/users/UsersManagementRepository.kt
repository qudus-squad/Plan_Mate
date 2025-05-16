package org.qudus.squad.ui.features.users

interface UsersManagementRepository {

    suspend fun getAllUsers()
    suspend fun createNewUser()
    suspend fun deleteUser()
    }