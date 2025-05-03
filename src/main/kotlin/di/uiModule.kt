package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.ui.CreateNewMateUser
import org.qudus.squad.ui.ManageProjectsUI
import org.qudus.squad.ui.SignIn

val uiModule = module {
    single { CreateNewMateUser(get()) }

    single { ManageProjectsUI(get()) }

    single { SignIn(get()) }
}