package org.qudus.squad.di

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.koin.dsl.module
import org.qudus.squad.data.data_source.log_data_source.remote.LogEntryDto
import org.qudus.squad.data.data_source.user_data_source.remote.UserDto

val mongoDatabaseModule = module {
    single<MongoClient> {
        MongoClient.create(System.getenv("MONGO_URI"))
    }
    single<MongoDatabase> {
        get<MongoClient>().getDatabase("plan_mate")
    }
    single<MongoCollection<UserDto>> {
        get<MongoDatabase>().getCollection("users", UserDto::class.java)
    }

    single<MongoCollection<LogEntryDto>> {
        get<MongoDatabase>().getCollection("logs", LogEntryDto::class.java)
    }
}



