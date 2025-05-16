package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.data.data_source.authntication_data_source.AuthenticationDataSource
import org.qudus.squad.data.data_source.authntication_data_source.remote.MongoAuthenticationDataSource
import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.data.data_source.log_data_source.remote.MongoLogDataSource
import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.data.data_source.project_data_source.remote.MongoProjectDataSource
import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.data.data_source.task_data_source.remote.MongoTaskDataSource
import org.qudus.squad.data.data_source.task_state_data_source.TaskStateDataSource
import org.qudus.squad.data.data_source.task_state_data_source.remote.MongoTaskStateDataSource
import org.qudus.squad.data.data_source.user_data_source.UserDataSource
import org.qudus.squad.data.data_source.user_data_source.remote.MongoUserDataSource
import org.qudus.squad.data.repositories.*
import org.qudus.squad.logic.repositories.*
import org.qudus.squad.logic.validation.UserDataValidationUseCase
import org.qudus.squad.ui.utils.DataHashing
import org.qudus.squad.ui.utils.EncryptionByUsingMD5

val appModule = module {

    single<DataHashing> { EncryptionByUsingMD5() }

    single<AuthenticationDataSource> {
        MongoAuthenticationDataSource(get(), get())
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImplementation(get())
    }


    single<LogDataSource> {
        MongoLogDataSource(get())
    }

    single<LogRepository> {
        LogRepositoryImplementation(get())
    }

    single<ProjectDataSource> {
        MongoProjectDataSource(get())
    }

    single<ProjectRepository> { ProjectRepositoryImplementation(get()) }

    single<TaskDataSource> { MongoTaskDataSource(get()) }

    single<TaskRepository> {
        TaskRepositoryImplementation(get())
    }


    single<UserDataSource> {
        MongoUserDataSource(get())
    }

    single { UserDataValidationUseCase() }

    single<UserRepository> { UserRepositoryImplementation(get()) }

    single<TaskStateDataSource> { MongoTaskStateDataSource(get()) }

    single<TaskStateRepository> { TaskStateRepositoryImplementation(get()) }
}