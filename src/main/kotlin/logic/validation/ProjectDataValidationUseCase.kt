package org.qudus.squad.logic.validation

import logic.exceptions.InvalidProjectDescriptionException
import logic.exceptions.InvalidProjectIdException
import logic.exceptions.InvalidProjectTitleException
import org.qudus.squad.logic.model.Project


class ProjectDataValidationUseCase {
    fun validateProjectData(project: Project) {
        if (!isValidProjectTitle(project.title)) {
            throw InvalidProjectTitleException(INVALID_PROJECT_TITLE)
        }
        if (!isValidProjectDescription(project.description)) {
            throw InvalidProjectDescriptionException(INVALID_PROJECT_DESCRIPTION)
        }
    }

    fun validateProjectId(id: String) {
        if (!isValidProjectId(id)) throw InvalidProjectIdException(INVALID_PROJECT_ID)
    }

    private fun isValidProjectId(title: String): Boolean {
        return title.isNotEmpty() && title.isNotBlank()
    }

    fun isValidProjectTitle(title: String): Boolean {
        return title.isNotEmpty() && title.isNotBlank()
    }

    private fun isValidProjectDescription(description: String): Boolean {
        return description.isNotEmpty() && description.isNotBlank()
    }

    companion object {
        const val INVALID_PROJECT_TITLE = "Project Title cannot be empty "
        const val INVALID_PROJECT_DESCRIPTION = "Project Description cannot be empty "
        const val INVALID_PROJECT_ID = "Project id cannot be empty "
    }
}