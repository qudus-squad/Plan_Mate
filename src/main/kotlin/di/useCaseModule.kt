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
import org.koin.dsl.module
import org.qudus.squad.logic.use_cases.project.GetProjectByIdUseCase
import org.qudus.squad.logic.utils.DataHashing
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.logic.validation.ProjectDataValidationUseCase
import org.qudus.squad.logic.validation.TaskDataValidationUseCase
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.model.entity.LoginSession

val useCaseModule = module {

    single { UserDataValidationUseCase() }

    single { ProjectDataValidationUseCase() }

    single { LoginSession() }

    single { SignInUseCase(get(), get()) }

    single { GetChangeLogEntriesForTargetIdUseCase(get()) }

    single { CreateNewProjectUseCase(get(), get()) }

    single { DeleteProjectUseCase(get()) }

    single { EditProjectUseCase(get()) }

    single { GetAllProjectsUseCase(get()) }

    single { GetProjectByIdUseCase(get()) }

    single { AssignTaskToUserUseCase(get()) }

    single { GetAllTasksByProjectIdUseCase(get()) }

    single { GetTasksByStateUseCase(get()) }

    single { CreateNewTaskUseCase(get(),get(),get()) }

    single { DeleteTaskUseCase(get(),get(),get()) }

    single<DataHashing> { EncryptionByUsingMD5() }

    single { SaveLogUseCase(get()) }

    single { AddNewUserUseCase(get(),get(),get()) }

    single { GetAllLogsUseCase(get()) }

    single { EditTaskUseCase(get() , get() , get()) }

    single { TaskDataValidationUseCase() }

}
