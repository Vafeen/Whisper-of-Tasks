package ru.vafeen.whisperoftasks.domain.noui.database.usecase

import kotlinx.coroutines.flow.Flow
import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.entity.Reminder

internal class GetAllAsFlowRemindersUseCase(private val db: AppDatabase) {
    operator fun invoke(): Flow<List<Reminder>> = db.reminderDao().getAllAsFlow()
}