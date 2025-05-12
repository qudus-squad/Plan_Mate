package org.qudus.squad.ui.controlPanel.admin

import logic.use_cases.user.AddNewUserUseCase
import logic.use_cases.user.GetAllUsersUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.use_cases.user.DeleteUserUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.tablesDisplay.UsersTableDisplay
import org.qudus.squad.ui.utils.DataHashing
import org.qudus.squad.ui.utils.StringAlignment.center

class ManageUsers(
    private val user: User,
) {
    ///////////////////////////// MANAGE USERS ///////////////////////////// ( 0 - > 2 )

    suspend fun getAllUsers() {
        val display = UsersTableDisplay()
        val repository: UserRepository = getKoin().get()
        val getAllUsers = GetAllUsersUseCase(repository)
        val allUsers = getAllUsers.getAllUsers()
        if (allUsers.isNotEmpty()) {
            display.displayUsers(allUsers)
            manageUsersPanel()
        } else targetNotFound("USERS")
        return
    }

    private suspend fun createNewUser() {
        try {
            val repository: UserRepository = getKoin().get()
            val validation: UserDataValidationUseCase = getKoin().get()
            val hashing: DataHashing = getKoin().get()
            val createNewUser = AddNewUserUseCase(repository, validation, hashing)
            println("ENTER USER NAME : ")
            val titleSelected = readlnOrNull()?.trim() ?: ""
            println("ENTER USER PASSWORD : ")
            val passwordSelected = readlnOrNull()?.trim() ?: ""
            println("SELECT USER ROLE")
            println("1.ADMIN")
            println("2.MATE")
            val roleChoice = readlnOrNull()?.trim() ?: ""
            val selectedRole = when (roleChoice) {
                "1" -> UserRole.ADMIN
                else -> UserRole.MATE
            }
            if (createNewUser.addUser(
                    currentUserRole = user.role,
                    username = titleSelected,
                    password = passwordSelected,
                    userRole = selectedRole,
                )
            ) {
                println("USER $titleSelected CREATED ")
            }
        } catch (e: Exception) {
            println("FAILED TO CREATE USER PRESS ENTER TO TRY AGAIN 0 TO EXIT ")
            if (readlnOrNull()?.trim() == "0") return manageUsersPanel() else createNewUser()
        }
    }

    private suspend fun deleteUser() {
        try {
            val deleteUserUseCase: DeleteUserUseCase = getKoin().get()
            println("ENTER USER ID : ")
            val idSelected = readlnOrNull()?.trim() ?: ""
            deleteUserUseCase.deleteUser(user, idSelected)
            println("USER WITH : '$idSelected' ID DELETED")
        } catch (e: Exception) {
            println("FAILED TO DELETE USER PRESS ENTER TO TRY AGAIN OR 0 TO EXIT")
            if (readlnOrNull()?.trim() == "0") return manageUsersPanel() else deleteUser()

        }
    }

    private suspend fun manageUsersPanel() {
        println("┌───────────────────────────┐")
        println("│         MANAGE USERS      │")
        println("│───────────────────────────│")
        println("│1- DELETE USER BY ID       │")
        println("│2- CREATE NEW USER         │")
        println("│0- RETURN                  │")
        println("└───────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> deleteUser()
            "2" -> createNewUser() // IF FAILED RETURN
            "0" -> return
        }
    }

    ///////////////////////////// ERRORS ////////////////////////////// ( 0 - > 3 )

    private fun targetNotFound(targetType: String) {
        println("┌${"─".repeat(30)}┐")
        println("│${"NO $targetType FOUND".center(30)}│")
        println("└${"─".repeat(30)}┘")
    }

    private fun idNotFound() {
        println("┌───────────────────────────────┐")
        println("│      INVALID ID TRY AGAIN     │")
        println("└───────────────────────────────┘")
    }

    private fun invalidOption() {
        println("┌───────────────────────────────┐")
        println("│   INVALID OPTION TRY AGAIN    │")
        println("└───────────────────────────────┘")
    }
}