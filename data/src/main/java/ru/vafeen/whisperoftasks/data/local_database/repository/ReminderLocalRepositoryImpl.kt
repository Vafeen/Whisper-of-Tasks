package ru.vafeen.whisperoftasks.data.local_database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vafeen.whisperoftasks.data.converters.ReminderConverter
import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

internal class ReminderLocalRepositoryImpl(
    db: AppDatabase,
    private val reminderConverter: ReminderConverter
) : ReminderLocalRepository {
    private val reminderDao = db.reminderDao()

    override fun getAllRemindersAsFlow(): Flow<List<Reminder>> = reminderDao.getAllAsFlow().map {
        reminderConverter.convertABList(it)
    }

    override fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        reminderDao.getReminderByIdOfReminder(idOfReminder)?.let { reminderConverter.convertAB(it) }


    override suspend fun insertAllReminders(vararg entity: Reminder) {
        // Преобразуем Reminder в ReminderEntity и вставляем в базу данных
        reminderDao.insertAll(*entity.map { reminderConverter.convertBA(it) }.toTypedArray())
    }

    override suspend fun insertAllReminders(entities: List<Reminder>) {
        // Преобразуем список Reminder в список ReminderEntity и вставляем в базу данных
        reminderDao.insertAll(*entities.map { reminderConverter.convertBA(it) }.toTypedArray())
    }

    override suspend fun deleteAllReminders(vararg entity: Reminder) =
        // Преобразуем Reminder в ReminderEntity и удаляем из базы данных
        reminderDao.delete(entities = entity.map { reminderConverter.convertBA(it) }.toTypedArray())


    override suspend fun deleteAllReminders(entities: List<Reminder>) =
        // Преобразуем список Reminder в список ReminderEntity и удаляем из базы данных
        reminderDao.delete(entities = reminderConverter.convertBAList(entities).toTypedArray())

}
