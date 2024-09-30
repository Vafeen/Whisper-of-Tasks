package ru.vafeen.reminder.noui.local_database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.reminder.noui.local_database.entity.Reminder

/**
 * "Abstract" class for manipulations with database
 */
class DatabaseRepository(db: AppDatabase) {
    private val reminderDao = db.reminderDao()
    fun getAllRemindersAsFlow(): Flow<List<Reminder>> = reminderDao.getAllAsFlow()
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        reminderDao.getReminderByIdOfReminder(idOfReminder = idOfReminder)


    /**
     * Inserting && Updating in database one or more lessons
     * @param entities [Set of entities to put in database]
     */
    suspend fun insertAllReminders(vararg entities: Reminder) =
        reminderDao.insertAll(entities = entities)

    /**
     * Deleting from database one or more lessons
     * @param entities [Set of entities to remove from database]
     */
    suspend fun deleteAllReminders(vararg entities: Reminder) =
        reminderDao.delete(entities = entities)


    /**
     * Updating in database one or more lessons
     * @param entities [Set of entities to update in database]
     */
    suspend fun updateAllReminders(vararg entities: Reminder) =
        reminderDao.update(entities = entities)


}