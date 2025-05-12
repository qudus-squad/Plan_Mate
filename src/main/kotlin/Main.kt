package org.qudus.squad

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import logic.exceptions.InvalidUserDataException
import logic.use_cases.authentication.SignInUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.logic.model.LoginSession
import org.qudus.squad.logic.model.UserRole
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.controlPanel.MateControlPanel
import org.qudus.squad.ui.controlPanel.TaskManagement
import org.qudus.squad.ui.controlPanel.admin.ManageProject
import org.qudus.squad.ui.controlPanel.admin.ManageUsers


fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }
    val signInUseCase: SignInUseCase = getKoin().get()
    val loginSession = LoginSession()
    val taskRepository: TaskRepository = getKoin().get()
    println("Enter your username")
    val username = readln()
    println("Enter you password")
    val password = readln()
    runBlocking {
        try {
            val deferred = async {
                signInUseCase.signIn(username, password)
            }
            val user = deferred.await()
            loginSession.currentUser = user
            when (user.role) {
                UserRole.ADMIN -> {
                    val taskManagement = TaskManagement(taskRepository, user)
                    val manageProject = ManageProject(user ,taskManagement )
                    val manageUsers = ManageUsers(user )

                    val adminControlPanel = AdminControlPanel(user, manageProject ,manageUsers )
                    adminControlPanel.adminStory()
                }

                UserRole.MATE -> {
                    val taskManagement = TaskManagement(taskRepository, user)
                    val mateControlPanel = MateControlPanel(user, taskManagement)
                    mateControlPanel.mateStory()
                }
            }
        } catch (e: InvalidUserDataException) {
            print(e.message)
        } catch (e: Exception) {
            print(e.message)
        }
    }
}