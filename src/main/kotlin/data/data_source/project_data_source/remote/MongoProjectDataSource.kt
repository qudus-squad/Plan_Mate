package org.qudus.squad.data.data_source.project_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.qudus.squad.data.data_source.project_data_source.*
import org.qudus.squad.data.data_source.provideCollection
import org.qudus.squad.model.entity.Project

class MongoProjectDataSource(
    private val mongoDatabase: MongoDatabase
) : ProjectDataSource {
    private val projectCollection = provideCollection(mongoDatabase, COLLECTION_NAME, ProjectDto::class.java)

    override suspend fun getAllProjects(): List<Project> {
        val projects = projectCollection.find().toList().map { projectDto -> projectDto.toProject() }
        if (projects.isEmpty()) throw InvalidToGetAllLException()
        return projects
    }

    override suspend fun deleteProjectById(id: String): Boolean {
        val isDeleted = projectCollection.deleteOne(Filters.eq(ID_FIELD, id)).wasAcknowledged()
        if (!isDeleted) throw InvalidToDeleteProjectException()
        return true
    }

    override suspend fun createNewProject(project: Project): Project {
        val projectDto = project.toProjectDto()
        val isInserted = projectCollection.insertOne(projectDto).wasAcknowledged()
        if (!isInserted) throw InvalidToAddProjectException()
        return project
    }

    override suspend fun getProjectById(id: String): Project {
        val projectDto =
            projectCollection.find(Filters.eq(ID_FIELD, id)).firstOrNull() ?: throw InvalidToGetByIdProjectException()
        return projectDto.toProject()
    }

    override suspend fun editProject(project: Project): Boolean {
        val projectDto = project.toProjectDto()
        val isEdited = projectCollection.replaceOne(Filters.eq(ID_FIELD, project.id), projectDto).wasAcknowledged()
        if (!isEdited) throw InvalidToEditProjectException()
        return true
    }

    companion object {
        const val ID_FIELD = "id"
        const val COLLECTION_NAME = "projects"
    }
}