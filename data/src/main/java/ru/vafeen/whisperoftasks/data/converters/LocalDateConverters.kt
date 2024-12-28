package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import ru.vafeen.whisperoftasks.domain.converter.BaseConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class LocalDateConverters : BaseConverter<LocalDate?, String?> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    override fun convertAB(a: LocalDate?): String? {
        return a?.format(formatter)
    }

    @TypeConverter
    override fun convertBA(b: String?): LocalDate? {
        return b?.let { LocalDate.parse(it, formatter) }
    }
}
