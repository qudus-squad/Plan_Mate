package org.qudus.squad.data.data_source.authntication_data_source

import org.qudus.squad.data.CredentialManager
import org.qudus.squad.model.MateUser
import org.qudus.squad.model.UserRole

class CsvAuthenticationDataSource(
    private val credentialManager: CredentialManager
): AuthenticationDataSource {

    override fun createMateUser(userRole: UserRole, user: MateUser) {
        credentialManager.saveCredentials(user.username,user.passwordHash)
    }

    override fun signIn(user: MateUser) {
        TODO("Not yet implemented")
    }
}