package org.qudus.squad.data.data_source.project_data_source

import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.ProjectCsvParser
import org.qudus.squad.data.data_source.WriteInFileUseCase
import org.qudus.squad.model.entity.Project
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.appendText

class CsvProjectDataSource(
    private val csvReader: CsvReader,
    private val projectCsvCsvParser: ProjectCsvParser,
    private val writeInFileUseCase: WriteInFileUseCase,
) : ProjectDataSource {

    private val projectFilePath: Path = Paths.get(PROJECTS_FILE)

    override fun getAllProjects(): List<Project> {
        return csvReader.read(PROJECTS_FILE).mapNotNull {
            try {
                projectCsvCsvParser.fromCsvRow(it)
            } catch (_: IllegalArgumentException) {
                null
            }
        }
    }

    override fun deleteProjectById(id: String): Boolean {
        val filteredProjects = getAllProjects().filterNot { it.id == id }
        val csvLines = filteredProjects.map(projectCsvCsvParser::toCsvRow)
        writeInFileUseCase.writeLinesToFile(PROJECTS_FILE, csvLines)

        return getAllProjects().any { it.id != id }
    }

    override fun createNewProject(project: Project): Project {
        val csvLine = projectCsvCsvParser.toCsvRow(project) + "\n"
        projectFilePath.appendText(csvLine)
        return project
    }

    override fun getProjectById(id: String): Project? {
        return getAllProjects().firstOrNull { it.id == id }
    }

    override fun editProject(project: Project) {
        val updatedProjects = getAllProjects().map {
            if (it.id == project.id) project else it
        }
        val csvLines = updatedProjects.map(projectCsvCsvParser::toCsvRow)
        writeInFileUseCase.writeLinesToFile(PROJECTS_FILE, csvLines)
    }

    companion object {
        const val PROJECTS_FILE = "projects.csv"
    }
}
