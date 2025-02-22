package ru.vafeen.whisperoftasks.domain.database.usecase

import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

class InsertAllRemindersUseCase(private val reminderLocalRepository: ReminderLocalRepository) {
    suspend operator fun invoke(vararg reminder: Reminder) =
        reminderLocalRepository.insertAllReminders(entity = reminder)

    suspend operator fun invoke(reminders: List<Reminder>) =
        reminderLocalRepository.insertAllReminders(reminders)
}