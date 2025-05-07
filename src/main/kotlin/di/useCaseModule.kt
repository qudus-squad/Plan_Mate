package org.qudus.squad.di

import logic.use_cases.authentication.SignInUseCase
import logic.use_cases.log.GetChangeLogEntriesForTargetIdUseCase
import logic.use_cases.log.SaveLogUseCase
import logic.use_cases.project.CreateNewProjectUseCase
import logic.use_cases.project.DeleteProjectUseCase
import logic.use_cases.project.EditProjectUseCase
import logic.use_cases.project.GetAllProjectsUseCase
import logic.use_cases.tasks.AssignTaskToUserUseCase
import logic.use_cases.tasks.CreateNewTaskUseCase
import logic.use_cases.tasks.DeleteTaskUseCase
import logic.use_cases.tasks.GetAllTasksByProjectIdUseCase
import logic.use_cases.tasks.GetTasksByStateUseCase
import logic.use_cases.user.AddNewUserUseCase
import org.koin.dsl.module
import org.qudus.squad.logic.utils.DataHashing
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase

val useCaseModule = module {

    single { UserDataValidationUseCase() }

    single { ProjectDataValidationUseCase() }

    single { TaskDataValidationUseCase() }

    single { SignInUseCase(get(), get()) }

    single { GetChangeLogEntriesForTargetIdUseCase(get()) }

    single { CreateNewProjectUseCase(get(), get()) }

    single { DeleteProjectUseCase(get()) }

    single { EditProjectUseCase(get()) }

    single { GetAllProjectsUseCase(get()) }



    single { AssignTaskToUserUseCase(get()) }

    single { GetAllTasksByProjectIdUseCase(get()) }

    single { GetTasksByStateUseCase(get()) }

    single { CreateNewTaskUseCase(get(),get(),get()) }

    single { DeleteTaskUseCase(get(),get(),get()) }

    single<DataHashing> { EncryptionByUsingMD5() }

    single { SaveLogUseCase(get()) }

    single { AddNewUserUseCase(get(),get(),get()) }
}
