package org.qudus.squad.data.csv.parser

import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class UserCsvParser : CsvParser<User> {
    override fun fromCsvRow(row: String): User {
        val cleanedLine = row.trim().replace("\"", "")
        val userList = cleanedLine.split(',')

        return User(
            username = userList[UserCsvColumnIndex.USERNAME],
            passwordHash = userList[UserCsvColumnIndex.PASSWORD_HASH],
            userId = userList[UserCsvColumnIndex.USER_ID],
            role = UserRole.valueOf(userList[UserCsvColumnIndex.USER_ROLE])
        )
    }

    override fun toCsvRow(model: User): String {
        return listOf(
            model.username,
            model.passwordHash,
            model.userId,
            model.role.name
        ).joinToString(",")
    }
}