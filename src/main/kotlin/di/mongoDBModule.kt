package org.qudus.squad.di

import org.koin.dsl.module

val mongoDBModule = module {

    // MongoDB Client
    single { MongoDBClient("mongodb+srv://planMate:palnMate@planmate.7duglq2.mongodb.net/?retryWrites=true&w=majority&appName=planMate") }

}
