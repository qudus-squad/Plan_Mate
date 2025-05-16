package org.qudus.squad.ui.features.tasks

class TasksControlScreen(
    private val tasksManagementRepository: TasksManagementRepository,
) {
    suspend fun manageProjectPanel(id: String = "") {
        println("┌─────────────────────────────────────────┐")
        println("│1- CREATE NEW TASK  │ 4- DELETE TASK     │")
        println("│2- EDIT TASK NAME   │ 5- ASSIGN   TASK   │")
        println("│3- EDIT TASK DES    │ 6- UNASSIGN TASK   │")
        println("│                0- RETURN                │")
        println("└─────────────────────────────────────────┘")
        suspend fun loop() {
            when (readlnOrNull()?.trim()) {
                "1" -> tasksManagementRepository.createNewTask(id)
                "2" -> tasksManagementRepository.editTaskNameUsingId(id)
                "3" -> tasksManagementRepository.editTaskDescriptionUsingId(id)
                "4" -> tasksManagementRepository.deleteTaskById(id)
                "5" -> tasksManagementRepository.assignTask(id)
                "6" -> tasksManagementRepository.unAssignTaskToUser(id)
                "0" -> return
                else -> {
                    println("invalid try again ")
                    loop()
                }
            }
        }
        loop()
    }

    suspend fun manageOneProjectPanel(
        id: String = "") { // after displaying one project
        println("┌───────────────────────────────────────────┐")
        println("│                  PROJECT                  │")
        println("│              1- EDIT PROJECT              │")
        println("│───────────────────────────────────────────│")
        println("│       TASKS        │       STATES         │")
        println("│2- CREATE NEW TASK  │7- CREATE NEW STATE   │")
        println("│3- DELETE TASK      │8- DELETE STATE       │")
        println("│4- EDIT TASK        │9- EDIT STATE         │")
        println("│───────────────────────────────────────────│")
        println("│6- ASSIGN TASK      │10- UNASSIGN TASK     │")
        println("│───────────────────────────────────────────│")
        println("│   0- RETURN        │  EMPTY - MAIN MENU   │")
        println("└───────────────────────────────────────────┘")
        suspend fun loop() {
            when (readlnOrNull()?.trim()) {
                // "1" -> getTasksByProjectId()
                "2" -> tasksManagementRepository.createNewTask(id)
                "3" -> tasksManagementRepository.deleteTaskById(id)
                "4" -> tasksManagementRepository.editTaskNameUsingId(id)
                "5" -> tasksManagementRepository.editTaskDescriptionUsingId(id)
                "6" -> tasksManagementRepository.assignTask(id)
                // "7" ->
                // "8" ->
                // "9" ->
                "10" -> tasksManagementRepository.unAssignTaskToUser(id)
                "0" -> return
                "" -> return
                else -> {
                    println("invalid try again ")
                    loop()
                }

            }
        }
        loop()
    }

}