package org.qudus.squad.data.csv

interface CsvParser <T>{
    fun fromCsvRow(row: List<String>): T
    fun toCsvRow(model: T): String
}