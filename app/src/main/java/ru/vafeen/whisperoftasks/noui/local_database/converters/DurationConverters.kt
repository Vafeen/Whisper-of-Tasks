package ru.vafeen.whisperoftasks.noui.local_database.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.vafeen.whisperoftasks.noui.duration.MyDuration

class DurationConverters {

    @TypeConverter
    fun myDurationToLong(myDuration: MyDuration?): Long? = myDuration?.milliSeconds

    @TypeConverters
    fun longToMyDuration(milliSeconds: Long?): MyDuration? =
        milliSeconds?.let { MyDuration(milliSeconds = it) }
}