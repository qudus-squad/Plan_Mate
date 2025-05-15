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
        try {
            return projectCollection.find().toList().map { projectDto ->
                projectDto.toProject()
            }
        } catch (e: Exception) {
            throw InvalidToGetAllLException(FAILED_GET_ALL_PROJECTS)
        }
    }

    override suspend fun deleteProjectById(id: String): Boolean {
        return try {
            val result = projectCollection.deleteOne(Filters.eq(ID_FIELD, id))
            result.deletedCount > 0
        } catch (e: Exception) {
            throw InvalidToDeleteProjectException(FAILED_DELETE_PROJECT)
        }
    }

    override suspend fun createNewProject(project: Project): Project {
        return try {
            val projectDto = project.toProjectDto()
            val result = projectCollection.insertOne(projectDto)
            if (result.insertedId != null) {
                project
            } else {
                throw InvalidToAddProjectException(FAILED_ADD_PROJECT)
            }        } catch (e: Exception) {
            throw InvalidToAddProjectException(FAILED_ADD_PROJECT)
        }
    }

    override suspend fun getProjectById(id: String): Project {
        return try {
            val projectDto = projectCollection.find(Filters.eq(ID_FIELD, id)).firstOrNull()
                ?: throw InvalidToGetByIdProjectException(FAILED_GET_PROJECT_BY_ID)
            projectDto.toProject()
        } catch (e: Exception) {
            throw InvalidToGetByIdProjectException(FAILED_GET_PROJECT_BY_ID)
        }
    }

    override suspend fun editProject(project: Project): Boolean {
        return try {
            val projectDto = project.toProjectDto()
            val result = projectCollection.replaceOne(Filters.eq(ID_FIELD, project.id), projectDto)
            result.matchedCount > 0 && result.modifiedCount > 0
        } catch (e: Exception) {
            throw InvalidToEditProjectException(FAILED_EDIT_PROJECT)
        }
    }

    companion object {
        const val ID_FIELD = "id"
        const val COLLECTION_NAME = "projects"
    }
}