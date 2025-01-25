package ru.vafeen.whisperoftasks.domain.database.usecase

import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

 class GetReminderByIdOfReminderUseCase(private val reminderLocalRepository: ReminderLocalRepository)  {
    suspend operator fun invoke(idOfReminder: Int): Reminder? =
        reminderLocalRepository.getReminderByIdOfReminder(idOfReminder = idOfReminder)
}