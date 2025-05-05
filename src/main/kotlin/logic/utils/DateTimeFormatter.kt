package org.qudus.squad.logic.utils
import kotlinx.datetime.LocalDateTime

object DateTimeFormatter {
    fun formatDateTimeForDisplay(dateTime: LocalDateTime): String {
        val date = dateTime.date
        val time = dateTime.time
        val hour = if (time.hour % 12 == 0) 12 else time.hour % 12
        val amPm = if (time.hour < 12) "AM" else "PM"
        val formattedHour = hour.toString().padStart(2, '0')
        val formattedMinute = time.minute.toString().padStart(2, '0')
        val formattedMonth = date.monthNumber.toString().padStart(2, '0')
        val formattedDay = date.dayOfMonth.toString().padStart(2, '0')

        return "${date.year}/$formattedMonth/$formattedDay $formattedHour:$formattedMinute $amPm"
    }
}