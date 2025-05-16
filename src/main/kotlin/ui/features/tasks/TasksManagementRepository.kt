package org.qudus.squad.ui.features.tasks

interface TasksManagementRepository  {

    suspend fun createNewTask(id: String)
    suspend fun editTaskNameUsingId(id: String)
    suspend fun editTaskDescriptionUsingId(id: String)
    suspend fun deleteTaskById(id: String)
    suspend fun assignTask(id: String)
    suspend fun unAssignTaskToUser(id: String)
}