package org.qudus.squad.ui.features

import org.qudus.squad.logic.usecases.mate.CreateNewMateUserUseCase
import org.qudus.squad.model.MateUser
import org.qudus.squad.model.UserRole
import org.qudus.squad.model.exceptions.InvalidUserDataException
import org.qudus.squad.model.exceptions.UnknownException
import org.qudus.squad.model.exceptions.UserAlreadyExistException

class CreateNewMateUser(
    private val createNewMateUserUseCase: CreateNewMateUserUseCase
) {
    fun createNewMateUser(currentUserRole: UserRole) {
        println("Enter username: ")
        val userName = readln()
        println("Enter Password")
        val password = readln()
        try {
            createNewMateUserUseCase.createNewMateUser(currentUserRole, MateUser(userName, password))
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