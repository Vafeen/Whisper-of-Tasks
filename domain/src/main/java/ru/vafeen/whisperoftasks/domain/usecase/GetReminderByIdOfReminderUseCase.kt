package ru.vafeen.whisperoftasks.domain.usecase

import ru.vafeen.whisperoftasks.domain.local_database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.models.Reminder

class GetReminderByIdOfReminderUseCase(private val reminderRepository: ReminderRepository) {
    operator fun invoke(idOfReminder: Int): Reminder? =
        reminderRepository.getReminderByIdOfReminder(idOfReminder = idOfReminder)
}