package ru.vafeen.whisperoftasks.domain.database.repository


import kotlinx.coroutines.flow.Flow
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

/**
 * "Abstract" class for manipulations with database
 */
interface ReminderLocalRepository {

    fun getAllRemindersAsFlow(): Flow<List<Reminder>>
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder?

    /**
     * Inserting && Updating in database one or more lessons
     * @param entity [Set of entities to put in database]
     */
    suspend fun insertAllReminders(vararg entity: Reminder)
    suspend fun insertAllReminders(entities: List<Reminder>)

    /**
     * Deleting from database one or more lessons
     * @param entity [Set of entities to remove from database]
     */
    suspend fun deleteAllReminders(vararg entity: Reminder)
    suspend fun deleteAllReminders(entities: List<Reminder>)
}