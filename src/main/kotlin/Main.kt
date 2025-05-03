package org.qudus.squad

import org.koin.core.context.startKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule


fun main() {
    startKoin {
        modules(appModule, useCaseModule, uiModule)
    }
                println("ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ   PLAN MATE  ΞΞΞΞΞΞΞΞΞΞΞΞΞΞΞ")
                println("┌───────────────────────────────────────────┐")
                println("│                 SIGN IN                   │")
                println("│           1- SIGN IN AS ADMIN \uD83D\uDD75\uFE0F\u200D♂\uFE0F")
                println("│           2- SIGN IN AS MATE \uD83E\uDD35\uFE0F")
                println("└───────────────────────────────────────────┘")
}
