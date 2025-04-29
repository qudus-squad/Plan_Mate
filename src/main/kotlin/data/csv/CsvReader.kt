package org.qudus.squad.data.csv

import okio.FileSystem
import okio.Path.Companion.toPath

class CsvReader {
    fun read(fileName: String): List<String> {
        val path = fileName.toPath()
        val fs = FileSystem.SYSTEM

        if (!fs.exists(path)) return emptyList()

        return fs.read(path) {
            generateSequence { readUtf8Line() }
                .filter { it.isNotBlank() }
                .toList()
        }
    }
}