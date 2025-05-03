package org.qudus.squad.data.data_source

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.sink
import okio.appendingSink

class WriteInFileUseCase {

    fun writeLinesToFile(filePath: String, lines: List<String>) {
        lines.forEach { writeLineToFile(filePath, it) }
    }

    fun writeLineToFile(filePath: String, line: String) {
        val path = filePath.toPath()
        FileSystem.SYSTEM.appendingSink(path).buffer().use {
            it.writeUtf8(line + "\n")
        }
    }
}
