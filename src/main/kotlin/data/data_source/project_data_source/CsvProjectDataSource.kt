package org.qudus.squad.data.data_source.project_data_source

import logic.exceptions.ProjectNotFoundException
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.ProjectCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.model.entity.Project
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.appendText

class CsvProjectDataSource(
    private val csvReader: CsvReader,
    private val projectCsvParser: ProjectCsvParser,
    private val writeInFileUseCase: WriteInFileUseCase,
) : ProjectDataSource {

    private val projectFilePath: Path = Paths.get(PROJECTS_FILE)

    override fun getAllProjects(): List<Project> {
        return csvReader.read(PROJECTS_FILE).mapNotNull {
            try {
                projectCsvParser.fromCsvRow(it)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }

    override fun deleteProjectById(id: String): Boolean {
        val allProjects = getAllProjects()

        val isProjectExists = allProjects.firstOrNull { it.id == id }
        if (isProjectExists == null) return false

        val updatedProjects = allProjects.filterNot { it.id == id }


        val csvLines = updatedProjects.map(projectCsvParser::toCsvRow)
        writeInFileUseCase.writeLinesToFile(PROJECTS_FILE, csvLines)

        return !updatedProjects.any { it.id == id }
    }

    override fun createNewProject(project: Project): Project {
        val csvLine = projectCsvParser.toCsvRow(project) + "\n"
        projectFilePath.appendText(csvLine)
        return project
    }

    override fun getProjectById(id: String): Project? {
        return getAllProjects().firstOrNull { it.id == id }
    }

    override fun editProject(project: Project): Boolean {
        if (getProjectById(project.id) == null){
            throw ProjectNotFoundException(PROJECT_NOT_FOUND)
        }
        val updatedProjects = getAllProjects().map {
            if (it.id == project.id) project else it
        }

        val csvLines = updatedProjects.map(projectCsvParser::toCsvRow)
        writeInFileUseCase.writeLinesToFile(PROJECTS_FILE, csvLines)

        return true
    }
    companion object {
        const val PROJECT_NOT_FOUND = "Project Not Found"
        const val PROJECTS_FILE = "projects.csv"
    }
}
