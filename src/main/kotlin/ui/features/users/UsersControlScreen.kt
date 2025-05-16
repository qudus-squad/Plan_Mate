package org.qudus.squad.ui.features.users

class UsersControlScreen (

    private val usersManagementRepository : UsersManagementRepository
){

    suspend fun manageUsersPanel() {
        println("┌───────────────────────────┐")
        println("│         MANAGE USERS      │")
        println("│───────────────────────────│")
        println("│1- DELETE USER BY ID       │")
        println("│2- CREATE NEW USER         │")
        println("│0- RETURN                  │")
        println("└───────────────────────────┘")
        when (readlnOrNull()?.trim()) {
            "1" -> usersManagementRepository.deleteUser()
            "2" -> usersManagementRepository.createNewUser()
            "0" -> return
        }
    }
}


/*
private fun targetNotFound(targetType: String) {
    println("┌${"─".repeat(30)}┐")
    println("│${"NO $targetType FOUND".center(30)}│")
    println("└${"─".repeat(30)}┘")
}

private fun idNotFound() {
    println("┌───────────────────────────────┐")
    println("│      INVALID ID TRY AGAIN     │")
    println("└───────────────────────────────┘")
}

private fun invalidOption() {
    println("┌───────────────────────────────┐")
    println("│   INVALID OPTION TRY AGAIN    │")
    println("└───────────────────────────────┘")
}
}*/
