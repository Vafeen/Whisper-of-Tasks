package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import ru.vafeen.universityschedule.domain.converter.TwoWayBaseConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class DateConverter : TwoWayBaseConverter<LocalDate, String> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    override fun convertAB(a: LocalDate): String = a.format(formatter)

    @TypeConverter
    override fun convertBA(b: String): LocalDate = LocalDate.parse(b, formatter)

}