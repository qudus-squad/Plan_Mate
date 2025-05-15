package org.qudus.squad.data.data_source

import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase

    fun <T : Any> provideCollection(database: MongoDatabase, collectionName: String, dataDto: Class<T>): MongoCollection<T> {
        return database.getCollection(collectionName, dataDto).withDocumentClass(dataDto)
    }
