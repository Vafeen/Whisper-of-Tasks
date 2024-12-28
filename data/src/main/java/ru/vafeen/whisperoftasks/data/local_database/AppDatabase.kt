package ru.vafeen.whisperoftasks.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vafeen.whisperoftasks.data.local_database.converters.DurationConverters
import ru.vafeen.whisperoftasks.data.local_database.converters.LocalDateConverters
import ru.vafeen.whisperoftasks.data.local_database.converters.LocalDateTimeConverters
import ru.vafeen.whisperoftasks.data.local_database.dao.ReminderDao
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

@Database(
    exportSchema = true,
    entities = [Reminder::class],
    version = 1
)
@TypeConverters(
    LocalDateTimeConverters::class,
    LocalDateConverters::class,
    DurationConverters::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

}