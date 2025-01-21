package ru.vafeen.whisperoftasks.domain.planner.usecase

import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

class RemoveEventUseCase(
    private val reminderLocalRepository: ReminderLocalRepository,
    private val scheduler: Scheduler
) {
    suspend operator fun invoke(vararg reminder: Reminder) {
        scheduler.cancelWork(reminder = reminder)
        reminderLocalRepository.deleteAllReminders(entity = reminder)
    }

    suspend operator fun invoke(reminders: List<Reminder>) {
        scheduler.cancelWork(reminders)
        reminderLocalRepository.deleteAllReminders(reminders)
    }
}