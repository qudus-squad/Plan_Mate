package org.qudus.squad.di

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.koin.dsl.module


val mongoDatabaseModule = module {
    single<MongoClient> {
        MongoClient.create(System.getenv("MONGO_URI"))
    }
    single<MongoDatabase> {
        get<MongoClient>().getDatabase("plan_mate")
    }
}



