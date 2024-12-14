package ru.vafeen.whisperoftasks.domain.noui.database.usecase

import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

internal class DeleteAllRemindersUseCase(private val db: AppDatabase) {
    suspend operator fun invoke(vararg reminder: Reminder) =
        db.reminderDao().delete(entities = reminder)
}