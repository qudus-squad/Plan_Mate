package org.qudus.squad.data.data_source.project_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.exceptions.ExceptionsUtils.Companion.FAILED_CREATING_PROJECT
import logic.exceptions.FailedCreatingProject
import logic.exceptions.ProjectNotFoundException
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource.Companion.PROJECT_NOT_FOUND
import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.model.entity.Project

class MongoProjectDataSource(
    private val mongoDatabase: MongoDatabase
) : ProjectDataSource {

    override suspend fun getAllProjects(): List<Project> {
        return provideProjectCollection(mongoDatabase).find().toList().map { projectDto ->
            projectDto.toProject()
        }
    }

    override suspend fun deleteProjectById(id: String): Boolean {
        return try {
            val result = provideProjectCollection(mongoDatabase).deleteOne(Filters.eq(ID_FIELD, id))
            result.deletedCount > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun createNewProject(project: Project): Project {
        val projectDto = project.toProjectDto()
        val result = provideProjectCollection(mongoDatabase).insertOne(projectDto)
        return result.insertedId?.let {
            project
        } ?: throw FailedCreatingProject(FAILED_CREATING_PROJECT)
    }

    override suspend fun getProjectById(id: String): Project {
        val projectDto = provideProjectCollection(mongoDatabase).find(Filters.eq(ID_FIELD, id)).firstOrNull()
            ?: throw ProjectNotFoundException(PROJECT_NOT_FOUND)
        return projectDto.toProject()
    }

    override suspend fun editProject(project: Project): Project {
        val projectDto = project.toProjectDto()
        provideProjectCollection(mongoDatabase).replaceOne(Filters.eq(ID_FIELD, project.id), projectDto).let {
            return project
        }
    }

    private fun provideProjectCollection(database: MongoDatabase): MongoCollection<ProjectDto> {
        return database.getCollection<ProjectDto>(COLLECTION_NAME).withDocumentClass(ProjectDto::class.java)
    }

    companion object {
        const val ID_FIELD = "id"
        const val COLLECTION_NAME = "projects"
    }

}