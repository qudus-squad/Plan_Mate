package org.qudus.squad.ui

import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.exceptions.UserAlreadyExistException
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
        } catch (e: UserAlreadyExistException) {
            print(e.message)
        } catch (e: Exception) {
            print(e.message)
        }
    }
}