package org.qudus.squad.data

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.UserCsvParser
import org.qudus.squad.data.data_source.user_data_source.CsvUserDataSource.Companion.USERS_FILE
import org.qudus.squad.logic.exceptions.InvalidUserDataException
import org.qudus.squad.logic.exceptions.UserAlreadyExistException
import org.qudus.squad.logic.utils.EncryptionByUsingMD5
import org.qudus.squad.model.entity.User

class CredentialManager {
    private val folderPath = "src/main/kotlin/data/security".toPath()
    private val usernamesPath = folderPath.resolve("usernames.csv")
    private val passwordsPath = folderPath.resolve("passwords.csv")
    private val fileSystem = FileSystem.SYSTEM
    private val encryptionByUsingMD5 = EncryptionByUsingMD5()
    private val csvReader = CsvReader()
    val userCsvParser = UserCsvParser()

    init {
        if (!fileSystem.exists(folderPath)) {
            fileSystem.createDirectories(folderPath)
        }
        if (!fileSystem.exists(usernamesPath)) {
            fileSystem.write(usernamesPath) {
                writeUtf8("HashedUsernames\n")
            }
        }
        if (!fileSystem.exists(passwordsPath)) {
            fileSystem.write(passwordsPath) {
                writeUtf8("HashedPasswords\n")
            }
        }
    }

    fun saveCredentials(username: String, password: String) {
        val hashedUsername = encryptionByUsingMD5.generateHash(username)
        val hashedPassword = encryptionByUsingMD5.generateHash(password)

        val storedUsernames = fileSystem.read(usernamesPath) { readUtf8() }.lines()
        if (storedUsernames.contains(hashedUsername)) {
            throw UserAlreadyExistException(USER_ALREADY_EXIST)
        }

        fileSystem.appendingSink(usernamesPath).buffer().use { it.writeUtf8("$hashedUsername\n") }
        fileSystem.appendingSink(passwordsPath).buffer().use { it.writeUtf8("$hashedPassword\n") }
    }

    fun validateCredentials(username: String, password: String): Boolean {
        val hashedUsername = encryptionByUsingMD5.generateHash(username)
        val hashedPassword = encryptionByUsingMD5.generateHash(password)

        val storedUsernames = fileSystem.read(usernamesPath) { readUtf8() }.lines()
        val storedPasswords = fileSystem.read(passwordsPath) { readUtf8() }.lines()

        for (index in 1 until storedUsernames.size) {
            if (storedUsernames[index] == hashedUsername && storedPasswords[index] == hashedPassword) {
                return true
            }
        }
        return false
    }

    fun signIn(username: String, password: String): User {
        val storedUsers = csvReader.read(USERS_FILE)
        val matchingUser = storedUsers.firstOrNull { line ->
            val user = userCsvParser.fromCsvRow(line)
            user.username == username && user.passwordHash == encryptionByUsingMD5.generateHash(password)
        }
        return matchingUser?.let { user ->
            userCsvParser.fromCsvRow(user)
        } ?: throw InvalidUserDataException(INVALID_USER_DATA)
    }

    companion object {
        const val USER_ALREADY_EXIST = "User Already Exist"
        const val INVALID_USER_DATA = "Invalid username or password"
    }
}