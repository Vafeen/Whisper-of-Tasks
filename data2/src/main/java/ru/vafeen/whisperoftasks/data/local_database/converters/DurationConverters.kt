package ru.vafeen.whisperoftasks.data.local_database.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.vafeen.whisperoftasks.data.duration.MyDuration

internal class DurationConverters {

    @TypeConverter
    fun myDurationToLong(myDuration: MyDuration?): Long? = myDuration?.milliSeconds

    @TypeConverters
    fun longToMyDuration(milliSeconds: Long?): MyDuration? =
        milliSeconds?.let { MyDuration(milliSeconds = it) }
}