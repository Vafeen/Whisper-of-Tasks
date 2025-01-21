package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.vafeen.whisperoftasks.domain.duration.MyDuration

internal class DurationConverter {

    @TypeConverter
    fun myDurationToLong(myDuration: MyDuration?): Long? = myDuration?.milliSeconds

    @TypeConverters
    fun longToMyDuration(milliSeconds: Long?): MyDuration? =
        milliSeconds?.let { MyDuration(milliSeconds = it) }
}