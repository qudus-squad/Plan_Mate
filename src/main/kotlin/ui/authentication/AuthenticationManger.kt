package org.qudus.squad.ui.authentication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import logic.use_cases.authentication.SignInUseCase
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.controlPanel.MateControlPanel
import kotlin.system.exitProcess

class AuthenticationManger(
    private val signInUseCase: SignInUseCase,
    private val loginSession: LoginSession,
    private val adminControlPanel: AdminControlPanel
) {
    val authScope = CoroutineScope(Dispatchers.IO)
    var userName = ""
    var password = ""

    fun start() {
        requestUserNameAndPassword()
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
                    println(e.message)
                    requestUserNameAndPassword()
                }
            }
        }
    }

    private fun requestUserNameAndPassword() {
        println("Enter your username (Press Enter to Exit)")
        userName = readlnOrNull() ?: ""
        println("Enter you password (Press Enter to Exit)")
        password = readlnOrNull() ?: ""
    }
}

