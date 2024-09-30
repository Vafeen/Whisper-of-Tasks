package ru.vafeen.reminder.noui.local_database.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.vafeen.reminder.noui.duration.MyDuration

class DurationConverters {

    @TypeConverter
    fun myDurationToLong(myDuration: MyDuration?): Long? = myDuration?.milliSeconds

    @TypeConverters
    fun longToMyDuration(milliSeconds: Long?): MyDuration? =
        milliSeconds?.let { MyDuration(milliSeconds = it) }
}