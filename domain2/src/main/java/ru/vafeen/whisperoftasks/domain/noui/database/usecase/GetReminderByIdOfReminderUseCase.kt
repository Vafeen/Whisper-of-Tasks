package ru.vafeen.whisperoftasks.domain.noui.database.usecase

import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

internal class GetReminderByIdOfReminderUseCase(private val db: AppDatabase) {
    operator fun invoke(idOfReminder: Int): Reminder? =
        db.reminderDao().getReminderByIdOfReminder(idOfReminder = idOfReminder)
}