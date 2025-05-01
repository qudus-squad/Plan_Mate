package org.qudus.squad.data.csv.parser

enum class TaskCsvColumnIndex (val index: Int){
    //Change to constants
    ID(0),
    TITLE(1),
    DESCRIPTION(2),
    PROJECT_ID(3),
    STATE_ID(4),
    STATE_NAME(5),
    CREATOR_USER_ID(6),
    CREATED_AT(7),
    LAST_UPDATED_AT(8)
}