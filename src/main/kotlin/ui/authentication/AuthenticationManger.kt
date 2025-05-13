package org.qudus.squad.ui.authentication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import logic.use_cases.authentication.SignInUseCase
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.controlPanel.MateControlPanel
import org.qudus.squad.ui.controlPanel.TaskManagement
import org.qudus.squad.ui.controlPanel.admin.ManageProject
import org.qudus.squad.ui.controlPanel.admin.ManageUsers
import kotlin.system.exitProcess

class AuthenticationManger(
    private val signInUseCase: SignInUseCase,
    private val taskRepository: TaskRepository,
    private val loginSession: LoginSession,
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
                            val taskManagement = TaskManagement(taskRepository, user)
                            val manageProject = ManageProject(user, taskManagement)
                            val manageUsers = ManageUsers(user)
                            val adminControlPanel = AdminControlPanel(user, manageProject, manageUsers)
                            adminControlPanel.adminStory()
                        }

                        UserRole.MATE -> {
                            val taskManagement = TaskManagement(taskRepository, user)
                            val mateControlPanel = MateControlPanel(user, taskManagement)
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

