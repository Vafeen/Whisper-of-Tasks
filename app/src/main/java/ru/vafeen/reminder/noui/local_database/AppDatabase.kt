package ru.vafeen.reminder.noui.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vafeen.reminder.noui.local_database.dao.ReminderDao
import ru.vafeen.reminder.noui.local_database.entity.Reminder

@Database(entities = [Reminder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

}