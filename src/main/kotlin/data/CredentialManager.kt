package org.qudus.squad.data
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.logic.EncryptionByUsingMD5
import org.qudus.squad.model.exceptions.UserAlreadyExistException

class CredentialManager {
    private val folderPath = "src/main/kotlin/data/security".toPath()
    private val usernamesPath = folderPath.resolve("usernames.csv")
    private val passwordsPath = folderPath.resolve("passwords.csv")
    private val fileSystem = FileSystem.SYSTEM
    private val encryptionByUsingMD5 = EncryptionByUsingMD5()

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
            throw UserAlreadyExistException("User Already Exist")
        }

        // Save new credentials if username doesn't exist
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
}