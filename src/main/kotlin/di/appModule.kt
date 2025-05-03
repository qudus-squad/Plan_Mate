package org.qudus.squad.di

import org.koin.dsl.module
import org.qudus.squad.data.CredentialManager
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.LogEntryCsvParser
import org.qudus.squad.data.csv.parser.ProjectCsvParser
import org.qudus.squad.data.csv.parser.TaskCsvParser
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
import org.qudus.squad.logic.utils.EncryptionByUsingMD5

val appModule = module {

    single { EncryptionByUsingMD5() }

    single { CredentialManager() }

    single<AuthenticationDataSource> {
        CsvAuthenticationDataSource(get())
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImplementation(get())
    }

    single { CsvReader() }

    single { LogEntryCsvParser() }

    single { WriteInFileUseCase() }

    single<LogDataSource> {
        CsvLogDataSource(get(), get(),get())
    }

    single<LogRepository> {
        LogRepositoryImpl(get())
    }

    single { ProjectCsvParser() }

    single<ProjectDataSource> { CsvProjectDataSource(get(), get(),get()) }

    single<ProjectRepository> { ProjectRepositoryImpl(get()) }

    single { TaskCsvParser() }

    single<TaskDataSource> {CsvTaskDataSource(get(),get(),get())  }

    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }

    single<TaskDataSource> { CsvTaskDataSource(get(),get(),get()) }

    single<StateRepository> { StateRepositoryImpl(get()) }

}