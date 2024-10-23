package ru.vafeen.whisperoftasks.data.local_database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

/**
 * "Abstract" class for manipulations with database
 */
interface DatabaseRepository {

    fun getAllRemindersAsFlow(): Flow<List<Reminder>>
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder?


    /**
     * Inserting && Updating in database one or more lessons
     * @param entity [Set of entities to put in database]
     */
    suspend fun insertAllReminders(vararg entity: Reminder)

    /**
     * Deleting from database one or more lessons
     * @param entity [Set of entities to remove from database]
     */
    suspend fun deleteAllReminders(vararg entity: Reminder)


    /**
     * Updating in database one or more lessons
     * @param entity [Set of entities to update in database]
     */
    suspend fun updateAllReminders(vararg entity: Reminder)

}