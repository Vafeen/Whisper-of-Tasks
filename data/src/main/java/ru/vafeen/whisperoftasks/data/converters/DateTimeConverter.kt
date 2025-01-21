package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import ru.vafeen.universityschedule.domain.converter.TwoWayBaseConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Конвертер для преобразования [LocalDateTime] в [Long] и обратно.
 * Используется для хранения объектов [LocalDateTime] в базе данных Room.
 */
internal class DateTimeConverter : TwoWayBaseConverter<LocalDateTime, Long> {

    /**
     * Преобразует [LocalDateTime] в [Long].
     *
     * @param a Входное значение типа [LocalDateTime].
     * @return Значение времени в миллисекундах с начала эпохи (Epoch Milliseconds).
     */
    @TypeConverter
    override fun convertAB(a: LocalDateTime): Long =
        a.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    /**
     * Преобразует [Long] в [LocalDateTime].
     *
     * @param b Входное значение времени в миллисекундах с начала эпохи (Epoch Milliseconds).
     * @return Объект [LocalDateTime], соответствующий времени [b].
     */
    @TypeConverter
    override fun convertBA(b: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(b), ZoneId.systemDefault())
}