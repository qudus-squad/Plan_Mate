package org.qudus.squad

import kotlinx.coroutines.runBlocking
import logic.exceptions.InvalidUserDataException
import logic.exceptions.UserAlreadyExistsException
import logic.use_cases.user.AddNewUserUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.model.entity.UserRole


fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }
    val addNewUserUseCase: AddNewUserUseCase = getKoin().get()
    runBlocking {
        try {
            val createdUser = addNewUserUseCase.addUser(
                currentUserRole = UserRole.ADMIN,
                username = "abdo",
                password = "0227693229",
                userRole = UserRole.MATE
            )
            if (createdUser) {
                println("user created")
            } else {
                println("failed to create user")
            }
        } catch (e: UserAlreadyExistsException) {
            println(e.message)
        } catch (e: InvalidUserDataException) {
            println(e.message)
        } catch (e: Exception) {
            print(e.message)
        }
    }
}
