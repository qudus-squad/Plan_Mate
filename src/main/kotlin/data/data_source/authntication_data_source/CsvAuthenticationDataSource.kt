package org.qudus.squad.data.data_source.authntication_data_source

import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.UserCsvParser
import org.qudus.squad.data.data_source.user_data_source.CsvUserDataSource.Companion.USERS_FILE
import model.exceptions.UserNotFoundException
import org.qudus.squad.logic.utils.DataHashing
import org.qudus.squad.model.entity.User

class CsvAuthenticationDataSource(
    private val csvReader: CsvReader,
    private val userCsvParser: UserCsvParser,
    private val hashing: DataHashing
) : AuthenticationDataSource {

    override fun signIn(username: String, password: String): User {
        val storedUsers = csvReader.read(USERS_FILE)
        val matchingUser = storedUsers.firstOrNull { line ->
            val user = userCsvParser.fromCsvRow(line)
            user.username == username && user.passwordHash == hashing.generateHash(password)
        }
        return matchingUser?.let { user ->
            userCsvParser.fromCsvRow(user)
        } ?: throw UserNotFoundException(USER_NOT_FOUND)

    }
    companion object {
        const val USER_NOT_FOUND = "User not found"
    }
}