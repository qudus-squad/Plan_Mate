package org.qudus.squad
import kotlinx.coroutines.runBlocking
import logic.use_cases.log.SaveLogUseCase
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.model.entity.LogEntry
import org.qudus.squad.model.entity.TargetType


fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }
    val saveLogUseCase: SaveLogUseCase = getKoin().get()
    runBlocking {
        saveLogUseCase.saveLog(
            logEntry = LogEntry(
                userName = "Inter Milan",
                targetId = "7676767676",
                targetType = TargetType.TASK ,
                action = "Done",
            )
        )
    }
}

