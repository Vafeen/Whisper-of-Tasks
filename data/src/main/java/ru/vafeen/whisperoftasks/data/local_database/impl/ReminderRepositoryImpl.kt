package ru.vafeen.whisperoftasks.data.local_database.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vafeen.whisperoftasks.data.converters.ReminderConverter
import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.domain.local_database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.models.Reminder

internal class ReminderRepositoryImpl(
    private val db: AppDatabase,
    private val reminderConverter: ReminderConverter
) : ReminderRepository {
    private val reminderDao = db.reminderDao()

    override fun getAllRemindersAsFlow(): Flow<List<Reminder>> = reminderDao.getAllAsFlow().map {
        reminderConverter.convertABList(it)
    }

    override fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        reminderDao.getReminderByIdOfReminder(idOfReminder)?.let {
            reminderConverter.convertAB(it)
        }

    override suspend fun insertAllReminders(vararg entity: Reminder) =
        reminderDao.insertAll(*reminderConverter.convertBAList(entity.toList()).toTypedArray())

    override suspend fun deleteAllReminders(vararg entity: Reminder) =
        reminderDao.delete(*reminderConverter.convertBAList(entity.toList()).toTypedArray())
}
