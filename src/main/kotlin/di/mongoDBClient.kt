package org.qudus.squad.di

import org.litote.kmongo.KMongo
import com.mongodb.client.MongoDatabase

class MongoDBClient(private val uri: String) {

    val client = KMongo.createClient(uri)
    val database: MongoDatabase = client.getDatabase(DATABASE_NAME)

    companion object{
        const val DATABASE_NAME = "planMate"
        const val MONGO_URI = "mongodb+srv://planMate:palnMate@planmate.7duglq2.mongodb.net/?retryWrites=true&w=majority&appName=planMate"
    }
}
