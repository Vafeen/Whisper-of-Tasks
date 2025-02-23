package ru.vafeen.whisperoftasks.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vafeen.whisperoftasks.data.converters.DateConverter
import ru.vafeen.whisperoftasks.data.converters.DateTimeConverter
import ru.vafeen.whisperoftasks.data.converters.DurationConverter
import ru.vafeen.whisperoftasks.data.local_database.dao.ReminderDao
import ru.vafeen.whisperoftasks.data.local_database.entity.ReminderEntity

@Database(
    exportSchema = true,
    entities = [ReminderEntity::class],
    version = 2
)
@TypeConverters(
    DateTimeConverter::class,
    DateConverter::class,
    DurationConverter::class
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

}