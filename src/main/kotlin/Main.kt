package org.qudus.squad

import kotlinx.coroutines.runBlocking
import logic.use_cases.tasks.EditTaskUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.qudus.squad.di.appModule
import org.qudus.squad.di.mongoDatabaseModule
import org.qudus.squad.di.uiModule
import org.qudus.squad.di.useCaseModule
import org.qudus.squad.model.entity.Task
import org.qudus.squad.model.entity.TaskState


fun main() {
    startKoin {
        modules(appModule, useCaseModule, mongoDatabaseModule, uiModule)
    }
}