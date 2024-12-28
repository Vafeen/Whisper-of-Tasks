package ru.vafeen.whisperoftasks.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vafeen.whisperoftasks.data.converters.DurationConverters
import ru.vafeen.whisperoftasks.data.converters.LocalDateConverters
import ru.vafeen.whisperoftasks.data.converters.LocalDateTimeConverters
import ru.vafeen.whisperoftasks.data.local_database.dao.ReminderDao
import ru.vafeen.whisperoftasks.data.local_database.entity.ReminderEntity

@Database(
    exportSchema = true,
    entities = [ReminderEntity::class],
    version = 2
)
@TypeConverters(
    LocalDateTimeConverters::class,
    LocalDateConverters::class,
    DurationConverters::class
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

}