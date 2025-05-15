package org.qudus.squad.data.data_source.user_data_source

import logic.exceptions.UserAlreadyExistsException
import logic.exceptions.UserNotFoundException
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.UserCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.model.entity.User

class CsvUserDataSource(
    private val csvReader: CsvReader,
    private val userCsvParser: UserCsvParser,
    private val writeInFileUseCase: WriteInFileUseCase

) : UserDataSource {
    override suspend fun addUser(user: User): Boolean {
        val storedUsers = csvReader.read(USERS_FILE)
        val usernameExists = storedUsers.any { line ->
            val userData = line.split(",")
            val storedUsername = userData[0]
            storedUsername == user.username
        }
        if (usernameExists) {
            throw UserAlreadyExistsException()
        }
        val csvLine = userCsvParser.toCsvRow(user)
        FileSystem.SYSTEM.appendingSink(USERS_FILE.toPath()).buffer().use { sink ->
            sink.writeUtf8(csvLine + "\n")
        }
        return true
    }


    override suspend fun getUserById(userId: String): User {
        return getAllUsers().firstOrNull { user -> isUserMatching(userId, user) } ?: throw UserNotFoundException()

    }

    override suspend fun getAllUsers(): List<User> {
        return csvReader.read(USERS_FILE).map { line ->
            userCsvParser.fromCsvRow(line)
        }
    }

    override suspend fun deleteUser(userId: String): Boolean {
        val filteredUsers = getAllUsers().filter { it.userId != userId }
        val csvLines = filteredUsers.map { user -> userCsvParser.toCsvRow(user) }
        writeInFileUseCase.writeLinesToFile(USERS_FILE, csvLines)
        return true
    }


    override suspend fun getUserByProjectId(projectId: String): User {
        TODO("Not yet implemented")
    }


    private fun isUserMatching(userId: String, user: User) = user.userId == userId

    companion object {
        const val USERS_FILE = "users.csv"
    }
}


