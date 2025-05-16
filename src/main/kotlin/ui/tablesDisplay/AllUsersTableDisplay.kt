package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.User
import org.qudus.squad.ui.utils.StringAlignment.center

class AllUsersTableDisplay {
    fun invoke(users: List<User>) {
        val topHeaderWidth = 25
        val idColumnWidth = 10
        val nameColumnWidth = 15
        val roleColumnWidth = 10
        val bordersWidth = 2
        val totalWidth = idColumnWidth + nameColumnWidth + roleColumnWidth + bordersWidth

        println(
            "Ξ".repeat(topHeaderWidth) + "   PLAN MATE   " + "Ξ".repeat(topHeaderWidth)
        )
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${"ALL USERS".center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println(
            "│${"USER ID".center(idColumnWidth)}" + "│${"USERNAME".center(nameColumnWidth)}" + "│${
                "USER ROLE".center(
                    roleColumnWidth
                )
            }│"
        )
        println("│${"─".repeat(totalWidth)}│")
        users.forEachIndexed { index, user ->
            val id = index + 1
            println(
                "│${id.toString().center(idColumnWidth)}" + "│${user.username.center(nameColumnWidth)}" + "│${
                    user.role.toString().center(roleColumnWidth)
                }│"
            )
        }
        println("└${"─".repeat(totalWidth)}┘")
    }
}