package org.qudus.squad.di

import org.litote.kmongo.KMongo
import com.mongodb.client.MongoDatabase

class MongoDBClient(private val uri: String) {

    val client = KMongo.createClient(uri)
    val database: MongoDatabase = client.getDatabase("planMate")

}
