package ru.vafeen.universityschedule.domain.database.usecase

import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

internal class UpdateAllRemindersUseCase(private val db: AppDatabase) {
    suspend operator fun invoke(vararg reminder: Reminder) =
        db.reminderDao().update(entities = reminder)
}