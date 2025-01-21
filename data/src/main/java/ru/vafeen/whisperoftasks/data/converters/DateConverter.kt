package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class DateConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun localDateToString(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun stringToLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }
}