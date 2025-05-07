package org.qudus.squad.data.data_source.project_data_source.remote

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import logic.exceptions.ExceptionsUtils.Companion.FAILED_CREATING_PROJECT
import logic.exceptions.FailedCreatingProject
import logic.exceptions.ProjectNotFoundException
import org.qudus.squad.data.data_source.project_data_source.CsvProjectDataSource.Companion.PROJECT_NOT_FOUND
import org.qudus.squad.data.data_source.project_data_source.ProjectDataSource
import org.qudus.squad.model.entity.Project

class MongoProjectDataSource(
    private val projectCollection: MongoCollection<ProjectDto>
) : ProjectDataSource {

    override suspend fun getAllProjects(): List<Project> {
        projectCollection.find().toList().apply {
            return this.map { it.toProject() }
        }
    }

    override suspend fun deleteProjectById(id: String): Boolean {
        return try {
            val result = projectCollection.deleteOne(Filters.eq("id", id))
            result.deletedCount > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun createNewProject(project: Project): Project {
        val projectDto = project.toProjectDto()
        val result = projectCollection.insertOne(projectDto)
        return result.insertedId?.let {
            project
        } ?: throw FailedCreatingProject(FAILED_CREATING_PROJECT)
    }

    override suspend fun getProjectById(id: String): Project {
        val projectDto = projectCollection.find(Filters.eq("id", id)).firstOrNull()
            ?: throw ProjectNotFoundException(PROJECT_NOT_FOUND)
        return projectDto.toProject()
    }

    override suspend fun editProject(project: Project): Boolean {
        val projectDto = project.toProjectDto()
        val result = projectCollection.replaceOne(Filters.eq("id", project.id), projectDto)
        return result.modifiedCount > 0
    }

}