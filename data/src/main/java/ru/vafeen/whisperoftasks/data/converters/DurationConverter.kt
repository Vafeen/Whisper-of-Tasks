package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import ru.vafeen.universityschedule.domain.converter.TwoWayBaseConverter
import ru.vafeen.whisperoftasks.domain.duration.MyDuration

internal class DurationConverter : TwoWayBaseConverter<MyDuration, Long> {
    @TypeConverter
    override fun convertAB(a: MyDuration): Long = a.milliSeconds
    @TypeConverter
    override fun convertBA(b: Long): MyDuration = MyDuration(milliSeconds = b)
}