package ru.vafeen.reminder.noui.local_database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import javax.inject.Inject


class DatabaseRepository @Inject constructor(private val db: AppDatabase) {
    private val reminderDao = db.reminderDao()

    fun getAllReminders(): Flow<List<Reminder>> = reminderDao.getAllAsFlow()
    suspend fun insertReminder(vararg reminder: Reminder) = reminderDao.insertAll(*reminder)
    suspend fun removeReminder(vararg reminder: Reminder) = reminderDao.delete(*reminder)
}