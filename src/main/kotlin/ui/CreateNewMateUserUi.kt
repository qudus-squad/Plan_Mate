package org.qudus.squad.ui

import logic.useCases.authentication.CreateNewUserUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.model.exceptions.InvalidUserDataException
import org.qudus.squad.model.exceptions.UnknownException
import org.qudus.squad.model.exceptions.UserAlreadyExistException

class CreateNewMateUserUi(
    private val createNewUserUseCase: CreateNewUserUseCase
) {
    fun createNewMateUser(currentUserRole: UserRole) {
        println("Enter username: ")
        val userName = readln()
        println("Enter Password")
        val password = readln()
        try {
            createNewUserUseCase.createNewMateUser(currentUserRole, User(userName, password , role =UserRole.MATE))
            println("User Added Successfully")
        } catch (e: AccessDeniedException) {
            println(e.message)
        } catch (e: InvalidUserDataException) {
            println(e.message)
        } catch (e: UserAlreadyExistException) {
            println(e.message)
        } catch (e: UnknownException) {
            println(e.message)
        }
    }
}