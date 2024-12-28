package ru.vafeen.whisperoftasks.domain.noui.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.database.usecase.UpdateAllRemindersUseCase
import ru.vafeen.whisperoftasks.data.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.GetReminderByIdOfReminderUseCase
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.InsertAllRemindersUseCase

internal class DatabaseRepositoryImpl(
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase,
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    private val deleteAllRemindersUseCase: DeleteAllRemindersUseCase,
    private val updateAllRemindersUseCase: UpdateAllRemindersUseCase
) : DatabaseRepository {
    override fun getAllRemindersAsFlow(): Flow<List<Reminder>> = getAllAsFlowRemindersUseCase()
    override fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        getReminderByIdOfReminderUseCase(idOfReminder = idOfReminder)

    /**
     * Inserting && Updating in database one or more lessons
     * @param lesson [Set of entities to put in database]
     */
    override suspend fun insertAllReminders(vararg entities: Reminder) =
        insertAllRemindersUseCase(reminder = entities)

    /**
     * Deleting from database one or more lessons
     * @param lesson [Set of entities to remove from database]
     */

    override suspend fun deleteAllReminders(vararg entity: Reminder) =
        deleteAllRemindersUseCase(reminder = entity)


    /**
     * Updating in database one or more lessons
     * @param lesson [Set of entities to update in database]
     */

    override suspend fun updateAllReminders(vararg entity: Reminder) =
        updateAllRemindersUseCase(reminder = entity)

}