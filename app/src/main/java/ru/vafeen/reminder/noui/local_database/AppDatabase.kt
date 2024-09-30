package ru.vafeen.reminder.noui.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vafeen.reminder.noui.local_database.converters.DTConverters
import ru.vafeen.reminder.noui.local_database.converters.DurationConverters
import ru.vafeen.reminder.noui.local_database.dao.ReminderDao
import ru.vafeen.reminder.noui.local_database.entity.Reminder

@Database(
    exportSchema = true,
    entities = [Reminder::class],
    version = 1
)
@TypeConverters(DTConverters::class, DurationConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

}