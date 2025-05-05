package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.LogEntryCsvParser
import org.qudus.squad.data.csv.parser.ProjectCsvParser
import org.qudus.squad.data.csv.parser.TaskCsvParser
import org.qudus.squad.data.csv.parser.UserCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.data.data_source.authntication_data_source.AuthenticationDataSource
import org.qudus.squad.data.data_source.authntication_data_source.CsvAuthenticationDataSource
import org.qudus.squad.data.data_source.log_data_source.CsvLogDataSource
import org.qudus.squad.data.data_source.log_data_source.LogDataSource
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource
import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.data.data_source.task_data_source.CsvTaskDataSource
import org.qudus.squad.data.data_source.task_data_source.TaskDataSource
import org.qudus.squad.data.repositories.*
import org.qudus.squad.logic.repositories.*
import org.qudus.squad.data.data_source.user_data_source.CsvUserDataSource
import org.qudus.squad.data.data_source.user_data_source.UserDataSource
import org.qudus.squad.data.repositories.AuthenticationRepositoryImplementation
import org.qudus.squad.data.repositories.LogRepositoryImpl
import org.qudus.squad.data.repositories.ProjectRepositoryImpl
import org.qudus.squad.data.repositories.StateRepositoryImpl
import org.qudus.squad.data.repositories.UserRepositoryImplementation
import org.qudus.squad.logic.repositories.AuthenticationRepository
import org.qudus.squad.logic.repositories.LogRepository
import org.qudus.squad.logic.repositories.ProjectRepository
import org.qudus.squad.logic.repositories.StateRepository
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.utils.DataHashing
import org.qudus.squad.logic.utils.EncryptionByUsingMD5

val appModule = module {
    single { UserCsvParser() }

    single  <DataHashing>{ EncryptionByUsingMD5() }

    single { CsvReader() }

    single<AuthenticationDataSource> {
        CsvAuthenticationDataSource(get(), get(), get())
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImplementation(get())
    }

    single { LogEntryCsvParser() }

    single { WriteInFileUseCase() }

    single<LogDataSource> {
        CsvLogDataSource(get(), get(), get())
    }

    single<LogRepository> {
        LogRepositoryImpl(get())
    }

    single { ProjectCsvParser() }

    single<ProjectDataSource> { CsvProjectDataSource(get(), get(), get()) }

    single<ProjectRepository> { ProjectRepositoryImpl(get()) }

    single { TaskCsvParser() }

    single<TaskDataSource> { CsvTaskDataSource(get(), get(), get()) }

    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }

    single<TaskDataSource> { CsvTaskDataSource(get(), get(), get()) }

    single<StateRepository> { StateRepositoryImpl(get()) }

    single<UserDataSource> { CsvUserDataSource(get(), get()) }

    single<UserRepository> { UserRepositoryImplementation(get()) }

}