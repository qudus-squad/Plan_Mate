package org.qudus.squad.data.csv.parser

import org.qudus.squad.data.csv.CsvParser
import org.qudus.squad.logic.utils.GenerateUUID
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class UserCsvParser : CsvParser<User> {
    override fun fromCsvRow(row: String): User {
        val cleanedLine = row.trim().replace("\"", "")
        val userList = cleanedLine.split(',')
        return User(
            username = userList[UserCsvColumnIndex.USERNAME.index],
            passwordHash =  userList[UserCsvColumnIndex.PASSWORD_HASH.index],
            userId  = GenerateUUID().generate(),
            role = UserRole.MATE
            //edited this part
        )
    }

    override fun toCsvRow(model: User): String {
        return listOf(
            model.username, model.passwordHash, model.userId, model.role
        ).joinToString(",")
    }
}