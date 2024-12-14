package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import ru.vafeen.whisperoftasks.domain.converter.BaseConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal class LocalDateTimeConverters : BaseConverter<LocalDateTime, Long> {

    @TypeConverter
    override fun convertAB(a: LocalDateTime): Long =
        a.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    @TypeConverter
    override fun convertBA(b: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(b), ZoneId.systemDefault())
}
