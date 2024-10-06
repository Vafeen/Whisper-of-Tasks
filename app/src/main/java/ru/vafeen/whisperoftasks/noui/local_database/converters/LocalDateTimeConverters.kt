package ru.vafeen.whisperoftasks.noui.local_database.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeConverters {

    @TypeConverter
    fun localDateTimeToLongMilliSeconds(dateTime: LocalDateTime): Long =
        dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()


    @TypeConverter
    fun longMilliSecondsToLocalDateTime(timestamp: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())

}