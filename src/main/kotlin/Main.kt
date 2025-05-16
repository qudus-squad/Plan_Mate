package org.qudus.squad

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import logic.use_cases.authentication.SignInUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.controlPanel.MateControlPanel
import kotlin.system.exitProcess

fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }
     val signInUseCase: SignInUseCase= getKoin().get()
    val loginSession: LoginSession= getKoin().get()
    val adminControlPanel: AdminControlPanel= getKoin().get()
    val authScope = CoroutineScope(Dispatchers.IO)
    var userName = ""
    var password = ""

    println("Enter your username (Press Enter to Exit)")
    userName = readlnOrNull() ?: ""
    println("Enter you password (Press Enter to Exit)")
    password = readlnOrNull() ?: ""
        authScope.launch {
            while (true) {
                try {
                    if (userName.isBlank() && password.isBlank()) {
                        println("Exiting...")
                        authScope.cancel()
                        exitProcess(0)
                    }
                    val user = signInUseCase.signIn(userName, password)
                    loginSession.currentUser = user
                    when (user.role) {
                        UserRole.ADMIN -> {
                            adminControlPanel.adminStory()
                        }

                        UserRole.MATE -> {

                            val mateControlPanel: MateControlPanel = getKoin().get()
                            mateControlPanel.mateStory()
                        }
                    }
                    break
                } catch (e: Exception) {
                    println("Enter your username (Press Enter to Exit)")
                    userName = readlnOrNull() ?: ""
                    println("Enter you password (Press Enter to Exit)")
                    password = readlnOrNull() ?: ""
                }
            }
        }
    }
