package org.qudus.squad.ui.features.projects

interface ProjectsManagementRepository  {

    suspend fun displayGetAllProjects()
    suspend fun viewProjectById()
    suspend fun deleteProject()
    suspend fun createNewProject()

}