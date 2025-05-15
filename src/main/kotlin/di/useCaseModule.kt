package org.qudus.squad.di

import logic.use_cases.authentication.SignInUseCase
import logic.use_cases.log.GetAllLogsUseCase
import logic.use_cases.log.GetChangeLogEntriesForTargetIdUseCase
import logic.use_cases.log.SaveLogUseCase
import logic.use_cases.project.CreateNewProjectUseCase
import logic.use_cases.project.DeleteProjectUseCase
import logic.use_cases.project.EditProjectUseCase
import logic.use_cases.project.GetAllProjectsUseCase
import logic.use_cases.tasks.*
import logic.use_cases.user.AddNewUserUseCase
import logic.use_cases.user.GetAllUsersUseCase
import org.koin.dsl.module
import org.qudus.squad.logic.use_cases.project.GetProjectByIdUseCase
import org.qudus.squad.logic.use_cases.tasks.UnAssignTaskUseCase
import org.qudus.squad.logic.use_cases.user.DeleteUserUseCase
import org.qudus.squad.logic.use_cases.user.GetUserByIdUseCase
import org.qudus.squad.logic.validation.LogEntryDataValidationUseCase
import org.qudus.squad.ui.utils.DataHashing
import org.qudus.squad.ui.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.logic.validation.UserRoleValidationUseCase
import org.qudus.squad.model.entity.LoginSession
import org.qudus.squad.ui.authentication.AuthenticationManger
import org.qudus.squad.ui.controlPanel.AdminControlPanel
import org.qudus.squad.ui.controlPanel.MateControlPanel
import org.qudus.squad.ui.controlPanel.admin.ManageProject
import org.qudus.squad.ui.controlPanel.admin.ManageUsers

val useCaseModule = module {

    single { UserDataValidationUseCase() }
    single { LogEntryDataValidationUseCase() }

    single { ProjectDataValidationUseCase() }

    single { UserRoleValidationUseCase() }

    single { LoginSession() }

    single { SignInUseCase(get(), get(), get()) }

    single { GetChangeLogEntriesForTargetIdUseCase(get(), get()) }

    single { CreateNewProjectUseCase(get(), get(), get(), get()) }

    single { DeleteProjectUseCase(get(), get(), get()) }

    single { EditProjectUseCase(get(), get(), get(), get()) }

    single { GetAllProjectsUseCase(get()) }

    single { GetProjectByIdUseCase(get(), get()) }

    single { AssignTaskToUserUseCase(get(), get() ,get()) }

    single { UnAssignTaskUseCase(get()) }

    single { GetAllTasksByProjectIdUseCase(get(), get()) }


    single { CreateNewTaskUseCase(get(), get(), get()) }

    single { DeleteTaskUseCase(get(), get(), get()) }

    single<DataHashing> { EncryptionByUsingMD5() }

    single { SaveLogUseCase(get(), get()) }

    single { AddNewUserUseCase(get(), get(), get(), get()) }

    single { GetAllLogsUseCase(get(), get()) }

    single { EditTaskUseCase(get(), get(), get()) }
    single { GetUserByIdUseCase(get(), get()) }
    single { GetAllUsersUseCase(get()) }
    single { TaskDataValidationUseCase() }
    single { DeleteUserUseCase(get(), get(), get()) }

    single { LoginSession() }
    single { ManageUsers(get()) }
    single {
        ManageProject(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single { AdminControlPanel(get(), get(), get()) }
    single { AuthenticationManger(get(), get(), get()) }
    single { MateControlPanel(get(), get()) }
}
