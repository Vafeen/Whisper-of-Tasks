package ru.vafeen.whisperoftasks.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.vafeen.whisperoftasks.domain.local_database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.models.Reminder

internal class GetAllAsFlowRemindersUseCase(private val reminderRepository: ReminderRepository) {
    operator fun invoke(): Flow<List<Reminder>> = reminderRepository.getAllRemindersAsFlow()
}