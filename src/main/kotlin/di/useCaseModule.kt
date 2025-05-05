package org.qudus.squad.di

import logic.use_cases.authentication.SignInUseCase
import logic.use_cases.project.CreateNewProjectUseCase
import logic.use_cases.project.DeleteProjectUseCase
import logic.use_cases.project.GetAllProjectsUseCase
import org.koin.dsl.module
import logic.use_cases.log.GetLogByTargetIdUseCase
import logic.use_cases.project.EditProjectUseCase
import logic.use_cases.taskState.CreateNewTaskStateUseCase
import logic.use_cases.taskState.DeleteTaskStateUseCase
import logic.use_cases.taskState.EditTaskStateUseCase
import logic.use_cases.taskState.GetAllTaskStatesByProjectIdUseCase
import logic.use_cases.tasks.AssignTaskToUserUseCase
import logic.use_cases.tasks.GetAllTasksByProjectIdUseCase
import logic.use_cases.tasks.GetTasksByStateUseCase
import logic.use_cases.user.AddNewUserUseCase
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.UserDataValidationUseCase

val useCaseModule = module {

    single { UserDataValidationUseCase() }

    single { SignInUseCase(get(),get()) }

    single { GetLogByTargetIdUseCase(get()) }

    single { CreateNewProjectUseCase(get()) }

    single { DeleteProjectUseCase(get()) }

    single { EditProjectUseCase(get()) }

    single { GetAllProjectsUseCase(get()) }

    single { CreateNewTaskStateUseCase(get()) }

    single { DeleteTaskStateUseCase(get()) }

    single { EditTaskStateUseCase(get()) }

    single { GetAllTaskStatesByProjectIdUseCase(get()) }

    single { AssignTaskToUserUseCase(get()) }

    single { GetAllTasksByProjectIdUseCase(get()) }

    single { GetTasksByStateUseCase(get()) }

    single { EncryptionByUsingMD5() }

    single { AddNewUserUseCase(get(), get(), get()) }
}
