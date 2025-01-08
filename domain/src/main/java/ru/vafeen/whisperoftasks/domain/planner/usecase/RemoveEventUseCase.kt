package ru.vafeen.whisperoftasks.domain.planner.usecase

import ru.vafeen.whisperoftasks.domain.database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder

class RemoveEventUseCase(private val reminderRepository: ReminderRepository) {
    suspend operator fun invoke(vararg reminder: Reminder) {
        // todo сюда нужен еще scheduler
        reminderRepository.deleteAllReminders(entity = reminder)
    }
}