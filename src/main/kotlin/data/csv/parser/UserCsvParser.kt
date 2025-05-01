/*package org.qudus.squad.data.csv.parser

import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.model.User

class UserCsvParser : CsvParser<User> {
    override fun fromCsvRow(row: String): User {
        val cleanedLine = row.trim().replace("\"", "")
        val userList = cleanedLine.split(',')
        return User(
            username = userList[UserCsvColumnIndex.USERNAME.index],
            passwordHash =  userList[UserCsvColumnIndex.PASSWORD_HASH.index],
            userId = userList[UserCsvColumnIndex.USER_ID.index]

        )
    }

    override fun toCsvRow(model: User): String {
        return listOf(
            model.username, model.passwordHash, model.userId, model.role
        ).joinToString(",")
    }
}*/