package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.ui.ManageProjectsUI

val uiModule = module {

    single { ManageProjectsUI(get()) }

}