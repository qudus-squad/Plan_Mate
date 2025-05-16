package org.qudus.squad.ui.features.users

import logic.use_cases.user.AddNewUserUseCase
import logic.use_cases.user.GetAllUsersUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.logic.use_cases.user.DeleteUserUseCase
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.tablesDisplay.AllUsersTableDisplay

class UsersManagementImplementation (
    private val usersTableDisplay: AllUsersTableDisplay,
    private val loginSession : LoginSession,


    ) : UsersManagementRepository{
    val getAllUsers : GetAllUsersUseCase = getKoin().get()
    private val createNewUser: AddNewUserUseCase = getKoin().get()
    private val deleteUserUseCase: DeleteUserUseCase = getKoin().get()


    override suspend fun getAllUsers(){
        val usersControlScreen : UsersControlScreen = getKoin().get()
        if (getAllUsers.getAllUsers().isNotEmpty()) {
            usersTableDisplay.invoke(getAllUsers.getAllUsers())
            usersControlScreen.manageUsersPanel()
        } else println("USERS")
        return
    }

    override suspend fun createNewUser() {
        val user = loginSession.currentUser

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
            createNewUser.addUser(
                    currentUserRole = user.role,
                    username = titleSelected,
                    password = passwordSelected,
                    userRole = selectedRole,
                )
                println("USER $titleSelected CREATED ")

        }

    override suspend fun deleteUser() {
        val user = loginSession.currentUser

        val usersControlScreen : UsersControlScreen = getKoin().get()

        val allUsers = getAllUsers.getAllUsers()
        if (allUsers.isNotEmpty()) {
            println("USERS")
            return }
        println("SELECT USER ID :")

        val selectedIndex = readlnOrNull()?.trim()?.toIntOrNull()?.minus(1)

        if (selectedIndex != null && selectedIndex in allUsers.indices) {
            val selectedUser = allUsers[selectedIndex]

            deleteUserUseCase.deleteUser(user, selectedUser.userId)
            println("USER '${selectedUser.username}' DELETED SUCCESSFULLY.")
            getAllUsers()
        } else {
            println()
            println("PRESS ENTER TO TRY AGAIN OR 0 TO EXIT")
            if (readlnOrNull()?.trim() == "0") return usersControlScreen.manageUsersPanel() else deleteUser()
        }
    }
    }