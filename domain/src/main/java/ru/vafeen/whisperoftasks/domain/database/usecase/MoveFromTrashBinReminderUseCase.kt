package ru.vafeen.whisperoftasks.domain.database.usecase

import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

class MoveFromTrashBinReminderUseCase(private val reminderLocalRepository: ReminderLocalRepository) {
    suspend operator fun invoke(vararg reminder: Reminder) =
        reminderLocalRepository.insertAllReminders(reminder.map { it.copy(isDeleted = false) })

    suspend operator fun invoke(reminders: List<Reminder>) =
        reminderLocalRepository.insertAllReminders(reminders.map { it.copy(isDeleted = false) })

}