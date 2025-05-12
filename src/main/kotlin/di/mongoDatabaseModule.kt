package org.qudus.squad.di

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.koin.dsl.module


val mongoDatabaseModule = module {
    single<MongoClient> {
        MongoClient.create("mongodb+srv://mousaber00:taIQGCADDO7U7pad@planmatecluster.hbbate9.mongodb.net/?retryWrites=true&w=majority&appName=PlanMateCluster")    }
    single<MongoDatabase> {
        get<MongoClient>().getDatabase("plan_mate")
    }
}