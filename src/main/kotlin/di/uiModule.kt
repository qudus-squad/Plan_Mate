package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.ui.features.CreateNewMateUser
import org.qudus.squad.ui.features.ManageProjectsUI
import org.qudus.squad.ui.features.SignIn

val uiModule = module {
    single { CreateNewMateUser(get()) }

    single { ManageProjectsUI(get()) }

    single { SignIn(get()) }
}