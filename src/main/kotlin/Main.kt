package org.qudus.squad

import logic.use_cases.authentication.SignInUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.logic.repositories.TaskRepository
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.ui.authentication.AuthenticationManger

fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }

    val signInUseCase: SignInUseCase = getKoin().get()
    val loginSession = LoginSession()
    val taskRepository: TaskRepository = getKoin().get()

    val authManager = AuthenticationManger(signInUseCase, taskRepository, loginSession)
    authManager.start()

}