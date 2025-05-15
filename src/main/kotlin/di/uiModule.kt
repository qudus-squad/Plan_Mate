package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.ui.authentication.AuthenticationManger
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.controlPanel.MateControlPanel
import org.qudus.squad.ui.controlPanel.TaskManagement
import org.qudus.squad.ui.controlPanel.admin.ManageProject
import org.qudus.squad.ui.controlPanel.admin.ManageUsers

val uiModule = module {
    single { LoginSession() }
    single { ManageUsers(get()) }
    single { TaskManagement(get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get()
    ) }
    single { ManageProject(get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
    ) }
    single { AdminControlPanel(get(), get(), get()) }
    single { AuthenticationManger(get(), get(), get()) }
    single { MateControlPanel(get(), get()) }
}