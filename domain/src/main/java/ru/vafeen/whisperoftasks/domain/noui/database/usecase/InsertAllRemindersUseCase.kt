package ru.vafeen.whisperoftasks.domain.noui.database.usecase

import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

internal class InsertAllRemindersUseCase(private val db: AppDatabase) {
    suspend operator fun invoke(vararg reminder: Reminder) =
        db.reminderDao().insertAll(entities = reminder)
}