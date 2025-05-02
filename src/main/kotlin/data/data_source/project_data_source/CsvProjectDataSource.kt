package org.qudus.squad.data.data_source.project_data_source

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.qudus.squad.data.csv.CsvReader
import org.qudus.squad.data.csv.parser.ProjectCsvParser
import org.qudus.squad.model.entity.Project

class CsvProjectDataSource(
    private val csvReader: CsvReader, private val projectCsvCsvParser: ProjectCsvParser
) : ProjectDataSource {
    override fun getAllProjects(): List<Project> {
        return csvReader.read(PROJECTS_FILE).mapNotNull { line ->
            try {
                projectCsvCsvParser.fromCsvRow(line)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    override fun deleteProjectById(id: String) {
        val projects = getAllProjects().filterNot {  project ->  project.id == id }

        FileSystem.SYSTEM.sink(PROJECTS_FILE.toPath()).buffer().use { sink ->
            projects.forEach { task ->
                val csvLine = projectCsvCsvParser.toCsvRow(task)
                sink.writeUtf8(csvLine + "\n")
            }
        }
    }

    override fun createNewProject(project: Project): Project{
        val csvLine = projectCsvCsvParser.toCsvRow(project)
        FileSystem.SYSTEM.appendingSink(PROJECTS_FILE.toPath()).buffer().use { sink ->
            sink.writeUtf8(csvLine + "\n")
        }
        return project
    }

    override fun getProjectById(id: String): Project? {
        return getAllProjects().firstOrNull{ task -> task.id == id }
    }

    override fun editProject(project: Project) {
        val updatedProjects = getAllProjects().map {
            if (it.id == project.id) project else it
        }

        FileSystem.SYSTEM.write(PROJECTS_FILE.toPath()) {
            updatedProjects.forEach { updatedProject ->
                writeUtf8(projectCsvCsvParser.toCsvRow(updatedProject) + "\n")
            }
        }
    }
    companion object {
        const val PROJECTS_FILE = "projects.csv"
    }
}