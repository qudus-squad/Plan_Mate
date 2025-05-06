package org.qudus.squad.data.data_source.user_data_source

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.UserCsvParser
import logic.exceptions.UserAlreadyExistsException
import org.qudus.squad.model.entity.User

class CsvUserDataSource(
    private val csvReader: CsvReader,
    private val userCsvParser: UserCsvParser
) : UserDataSource {
    override fun addUser(user: User): Boolean {
        val storedUsers = csvReader.read(USERS_FILE)
        val usernameExists = storedUsers.any { line ->
            val userData = line.split(",")
            val storedUsername = userData[0]
            storedUsername == user.username
        }
        if (usernameExists) {
            throw UserAlreadyExistsException(USER_ALREADY_EXIST)
        }
        val csvLine = userCsvParser.toCsvRow(user)
        FileSystem.SYSTEM.appendingSink(USERS_FILE.toPath()).buffer().use { sink ->
            sink.writeUtf8(csvLine + "\n")
        }
        return true
    }

    override fun getUserById(userId: String): User? {
        val targetUser = getAllUsers().firstOrNull { user ->
            isUserMatching(userId, user)
        }
        return targetUser
    }

    override fun getAllUsers(): List<User> {
        return csvReader.read(USERS_FILE).map { line ->
            userCsvParser.fromCsvRow(line)
        }
    }

    private fun isUserMatching(userId: String, user: User) = user.userId == userId

    companion object {
        const val USER_ALREADY_EXIST = "User Already Exists"
        const val USERS_FILE = "users.csv"
    }

}


