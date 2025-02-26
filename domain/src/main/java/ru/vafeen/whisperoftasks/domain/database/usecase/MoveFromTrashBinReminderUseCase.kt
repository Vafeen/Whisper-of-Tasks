package ru.vafeen.whisperoftasks.domain.database.usecase

import kotlinx.coroutines.flow.first
import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

class MoveFromTrashBinReminderUseCase(
    private val reminderLocalRepository: ReminderLocalRepository,
    private val scheduler: Scheduler
) {
    suspend operator fun invoke(reminders: List<Reminder>) {
        reminders.forEach {
            val reminder = it.copy(isDeleted = false)
            scheduler.planWork(reminder)
            reminderLocalRepository.insertAllReminders(reminder)
        }
    }

    suspend operator fun invoke(vararg reminder: Reminder) = invoke(reminder.toList())
}