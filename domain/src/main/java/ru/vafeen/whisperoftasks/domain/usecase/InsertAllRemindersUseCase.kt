package ru.vafeen.whisperoftasks.domain.usecase

import ru.vafeen.whisperoftasks.domain.local_database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.models.Reminder

internal class InsertAllRemindersUseCase(private val reminderRepository: ReminderRepository) {
    suspend operator fun invoke(vararg reminder: Reminder) =
        reminderRepository.insertAllReminders(entity = reminder)
}