package org.qudus.squad

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import logic.use_cases.authentication.SignInUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.model.entity.LoginSession


fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }

    val signInUseCase: SignInUseCase = getKoin().get()
    val loginSession = LoginSession()
    runBlocking {
        val deferredUser = async {
            signInUseCase.signIn(
                "RealMadrid",
                "1234456789",
            )
        }
        loginSession.currentUser = deferredUser.await()
    }
    println(loginSession.currentUser.username +", " + loginSession.currentUser.userId)
}