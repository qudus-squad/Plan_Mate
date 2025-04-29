package org.qudus.squad.data.csv

import java.io.File

class CsvReader {
    fun read(fileName: String): List<String> {
        val file = File(fileName)

        if (!file.exists()) return emptyList()

        return file.readLines()
            .filter { it.isNotBlank() }
    }
}