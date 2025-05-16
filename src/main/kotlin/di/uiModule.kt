package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.controlPanel.MateControlPanel
import org.qudus.squad.ui.features.logs.LogsManagementImplementation
import org.qudus.squad.ui.features.logs.LogsManagementRepository
import org.qudus.squad.ui.features.projects.ProjectsControlScreen
import org.qudus.squad.ui.features.projects.ProjectsManagementImplementation
import org.qudus.squad.ui.features.projects.ProjectsManagementRepository
import org.qudus.squad.ui.features.tasks.TasksControlScreen
import org.qudus.squad.ui.features.tasks.TasksManagementImplementation
import org.qudus.squad.ui.features.tasks.TasksManagementRepository
import org.qudus.squad.ui.features.users.UsersControlScreen
import org.qudus.squad.ui.features.users.UsersManagementImplementation
import org.qudus.squad.ui.features.users.UsersManagementRepository
import org.qudus.squad.ui.tablesDisplay.*
import org.qudus.squad.ui.utils.DateTimeFormatter

val uiModule = module {
single { DateTimeFormatter }

    single { UsersManagementImplementation(get() , get())  }

    single { AllProjectsTableDisplay(get()) }
    single { ProjectDetailsDisplay() }

    single { AllTasksTableDisplay(get()) }

    single { AllLogsTableDisplay(get()) }

    single { AllUsersTableDisplay() }

    single { LoginSession()}

    single <TasksManagementRepository>{
        TasksManagementImplementation(get() , get(), get() , get())
    }
    single { TasksControlScreen(get()) }
    single<ProjectsManagementRepository>{
        ProjectsManagementImplementation(get(),get() ,get(),get()) }

    single { ProjectsControlScreen(get()) }
    single { UsersControlScreen(get()) }
single <UsersManagementRepository>{UsersManagementImplementation(get() , get())  }

    single <LogsManagementRepository>{ LogsManagementImplementation(get()) }
    single { AdminControlPanel(get(), get(), get() ,get() ) }
    single { MateControlPanel(get(), get() ,get(), get() ) }
}