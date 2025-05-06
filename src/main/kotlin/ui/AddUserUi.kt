package org.qudus.squad.ui

import model.exceptions.InvalidUserDataException
import model.exceptions.UserAlreadyExistsException
import logic.use_cases.user.AddNewUserUseCase
import org.qudus.squad.model.entity.UserRole

class AddUserUi(
    private val addNewUserUseCase: AddNewUserUseCase,
) {
    fun addUserUI(currentUserRole: UserRole) {
        try {
            print("Enter username: ")
            val userName = readln()
            print("Enter Password: ")
            val password = readln()
            val result = addNewUserUseCase.addUser(
                currentUserRole = currentUserRole,
                username = userName,
                password = password,
                userRole = UserRole.MATE
            )
            print(result)
        } catch (e: InvalidUserDataException) {
            print(e.message)
        } catch (e: UserAlreadyExistsException) {
            print(e.message)
        } catch (e: Exception) {
            print(e.message)
        }
    }
}