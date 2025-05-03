package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.User

class UsersTableDisplay() {

    fun displayUsers(users: List<User>) {

        val idWidth = 10
        val nameWidth = 15
        val roleWidth = 10
        val borderWidth = 8
        val totalWidth = idWidth + nameWidth + roleWidth + borderWidth

        println(
            "Ξ"
                .repeat(totalWidth / 4) + "   PLAN MATE   " + "Ξ"
                .repeat(totalWidth / 4)
        )
        println(
            "┌" + "─"
                .repeat(totalWidth - 6) + "┐"
        )
        println(
            "│" + "ALL USERS"
                .padStart((totalWidth - 6 + "ALL USERS".length) / 2)
                .padEnd(totalWidth - 6) + "│"
        )
        println(
            "├" + "─"
                .repeat(idWidth) + "┬" + "─"
                .repeat(nameWidth) + "┬" + "─"
                .repeat(roleWidth) + "┤"
        )
        println(
            "│" + "USER ID"
                .padStart(idWidth / 6)
                .padEnd(idWidth) +
                    "│" + "USER NAME".padStart(nameWidth / 6)
                .padEnd(nameWidth) +
                    "│" + "USER ROLE".padStart(roleWidth / 6)
                .padEnd(roleWidth) + "│"
        )
        println(
            "├" + "─"
                .repeat(idWidth) + "┼" + "─"
                .repeat(nameWidth) + "┼" + "─"
                .repeat(roleWidth) + "┤"
        )


        users.forEach { user ->
            println(
                "│" + user.userId
                    .padStart(idWidth / 2 + user.userId.length / 2)
                    .padEnd(idWidth) +
                        "│" + user.username
                    .padStart(nameWidth / 2 + user.username.length / 2)
                    .padEnd(nameWidth) +
                        "│" + user.role.toString()
                    .padStart(roleWidth / 2 + user.role.toString().length / 2)
                    .padEnd(roleWidth) + "│"
            )
        }
        println(
            "└" + "─"
                .repeat(idWidth) + "┴" + "─"
                .repeat(nameWidth) + "┴" + "─"
                .repeat(roleWidth) + "┘"
        )
    }
}