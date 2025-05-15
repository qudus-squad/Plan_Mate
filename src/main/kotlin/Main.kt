package org.qudus.squad

import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.ui.authentication.AuthenticationManger

fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }

    val authManager: AuthenticationManger = getKoin().get()
    authManager.start()

}