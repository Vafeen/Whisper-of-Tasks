package ru.vafeen.whisperoftasks.data.mock.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import ru.vafeen.whisperoftasks.domain.database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

internal class MockReminderRepositoryImpl : ReminderRepository {
    private var reminders = mutableListOf<Reminder>()
    private val remindersFlow = MutableStateFlow(reminders.toList())
    override fun getAllRemindersAsFlow(): Flow<List<Reminder>> = remindersFlow

    override suspend fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        remindersFlow.first().firstOrNull {
            it.idOfReminder == idOfReminder
        }

    override suspend fun insertAllReminders(vararg entity: Reminder) {
        reminders.addAll(entity)
        updateFlow()
    }

    override suspend fun deleteAllReminders(vararg entity: Reminder) {
        reminders.removeAll(entity.toSet())
        updateFlow()
    }

    override suspend fun updateAllReminders(vararg entity: Reminder) {
        val ids = entity.map { it.idOfReminder }
        reminders.removeAll {
            it.idOfReminder in ids
        }
        reminders.addAll(entity)
        updateFlow()
    }

    private suspend fun updateFlow() {
        remindersFlow.emit(reminders)
    }
}