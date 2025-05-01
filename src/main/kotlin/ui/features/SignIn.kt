package org.qudus.squad.ui.features

import org.qudus.squad.data.CredentialManager
import org.qudus.squad.data.data_source.authntication_data_source.CsvAuthenticationDataSource
import org.qudus.squad.data.repositories.AuthenticationRepositoryImplementation
import org.qudus.squad.logic.useCases.authentication.SignInUseCase
import org.qudus.squad.logic.validation.UserDataValidator
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.model.exceptions.InvalidUserDataException

class SignIn(
    private val signInUseCase: SignInUseCase
) {
    fun signIn() {
        println("Enter your username: ")
        val userName = readln()
        println("Enter your password: ")
        val password = readln()

        try {
            val isUserSignedIn = (signInUseCase.signIn(User(userName, password , role = UserRole.MATE)))
            if (isUserSignedIn) {
                println("Signed in Successfully")
            } else {
                println("Wrong username or password")
            }
        } catch (e: InvalidUserDataException) {
            println(e.message)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}

fun main() {
    val credentialManager = CredentialManager()
    val authenticationDataSource = CsvAuthenticationDataSource(credentialManager)
    val authenticationRepository = AuthenticationRepositoryImplementation(authenticationDataSource)
    val userValidator = UserDataValidator()
    val signInUseCase = SignInUseCase(authenticationRepository, userValidator)
    val signIn = SignIn(signInUseCase)

    signIn.signIn()
}