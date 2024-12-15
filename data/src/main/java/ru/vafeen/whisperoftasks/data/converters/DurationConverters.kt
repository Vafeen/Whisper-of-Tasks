package ru.vafeen.whisperoftasks.data.converters

import androidx.room.TypeConverter
import ru.vafeen.whisperoftasks.domain.converter.BaseConverter
import ru.vafeen.whisperoftasks.domain.duration.MyDuration

internal class DurationConverters : BaseConverter<MyDuration?, Long?> {

    @TypeConverter
    override fun convertAB(a: MyDuration?): Long? = a?.milliSeconds

    @TypeConverter
    override fun convertBA(b: Long?): MyDuration? = b?.let { MyDuration(milliSeconds = it) }
}
