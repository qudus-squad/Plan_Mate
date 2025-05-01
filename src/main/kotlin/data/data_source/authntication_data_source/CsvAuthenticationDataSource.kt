package org.qudus.squad.data.data_source.authntication_data_source

import org.qudus.squad.data.CredentialManager
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class CsvAuthenticationDataSource(
    private val credentialManager: CredentialManager
) : AuthenticationDataSource {

    override fun createNewUser(userRole: UserRole, user: User) {
        credentialManager.saveCredentials(user.username, user.passwordHash)
    }

    override fun signIn(user: User): Boolean {
        return credentialManager.validateCredentials(user.username, user.passwordHash)
    }
}