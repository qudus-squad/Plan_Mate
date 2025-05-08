package org.qudus.squad.ui.tablesDisplay

import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole
import org.qudus.squad.ui.utils.StringAlignment.center

class UsersTableDisplay() {

    fun displayUsers(users: List<User>) {
        val topHeaderWidth =25
        val idColumnWidth = 36
        val nameColumnWidth = 15
        val roleColumnWidth = 10
        val bordersWidth = 2
        val totalWidth = idColumnWidth + nameColumnWidth + roleColumnWidth + bordersWidth

        println("Ξ".repeat(topHeaderWidth)
                + "   PLAN MATE   "
                + "Ξ".repeat(topHeaderWidth))
        println("┌${"─".repeat(totalWidth)}┐")
        println("│${"ALL USERS".center(totalWidth)}│")
        println("│${"─".repeat(totalWidth)}│")
        println("│${"USER ID".center(idColumnWidth )}" +
                "│${"USERNAME".center(nameColumnWidth )}" +
                "│${"USER ROLE".center(roleColumnWidth )}│")
        println("│${"─".repeat(totalWidth)}│")
        users.forEach { user ->
            println("│${user.userId.center(idColumnWidth )}" +
                    "│${user.username.center(nameColumnWidth )}" +
                    "│${user.role.toString().center(roleColumnWidth )}│")}
        println("└${"─".repeat(totalWidth)}┘")
    }
}
fun main ( ) {
    val users : List<User> = listOf(
        User(username = "user 1" , passwordHash = " " , role = UserRole.ADMIN)
        ,User(username = "user 2" , passwordHash = " " , role = UserRole.MATE)
        ,User(username = "user 3" , passwordHash = " " , role = UserRole.ADMIN)
        ,User(username = "user 4" , passwordHash = " " , role = UserRole.MATE))
    UsersTableDisplay().displayUsers(users)
}