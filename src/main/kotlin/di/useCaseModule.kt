package org.qudus.squad.di

import logic.useCases.authentication.CreateNewUserUseCase
import logic.useCases.authentication.SignInUseCase
import logic.useCases.project.CreateNewProjectUseCase
import logic.useCases.project.GetAllProjectsUseCase
import org.koin.dsl.module
import org.qudus.squad.logic.useCases.log.GetLogByTargetIdUseCase
import org.qudus.squad.logic.useCases.project.EditProjectUseCase
import org.qudus.squad.logic.useCases.taskState.CreateNewTaskStateUseCase
import org.qudus.squad.logic.useCases.taskState.DeleteTaskStateUseCase
import org.qudus.squad.logic.useCases.taskState.EditTaskStateUseCase
import org.qudus.squad.logic.useCases.taskState.GetAllTaskStatesByProjectIdUseCase
import org.qudus.squad.logic.useCases.tasks.AssignTaskToUserUseCase
import org.qudus.squad.logic.usecases.project.DeleteProjectUseCase
import org.qudus.squad.logic.validation.UserDataValidator

val useCaseModule = module {

    single { UserDataValidator() }

    single { CreateNewUserUseCase(get(), get()) }

    single { SignInUseCase(get(), get()) }

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
}