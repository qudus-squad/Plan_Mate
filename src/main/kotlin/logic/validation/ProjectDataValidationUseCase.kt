package org.qudus.squad.logic.validation

import logic.exceptions.InvalidProjectDescriptionException
import logic.exceptions.InvalidProjectTitleException
import org.qudus.squad.model.entity.Project


class ProjectDataValidationUseCase {
    fun validateProjectData(project: Project){
        if (!isValidTitle(project.title)){
            throw InvalidProjectTitleException(INVALID_PROJECT_TITLE)
        }
        if (!isValidDescription(project.description)){
            throw InvalidProjectDescriptionException(INVALID_PROJECT_DESCRIPTION)
        }
    }

    private fun isValidTitle(title: String): Boolean{
        return title.isNotEmpty() && title.isNotBlank()
    }

    private fun isValidDescription(description: String): Boolean{
        return description.isNotEmpty() && description.isNotBlank()
    }
    companion object {
        const val INVALID_PROJECT_TITLE = "Project Title cannot be empty "
        const val INVALID_PROJECT_DESCRIPTION = "Project Description cannot be empty "
    }
}