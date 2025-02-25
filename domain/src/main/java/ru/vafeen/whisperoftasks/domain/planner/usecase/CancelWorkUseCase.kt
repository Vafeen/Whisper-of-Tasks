package ru.vafeen.whisperoftasks.domain.planner.usecase

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

class CancelWorkUseCase(private val scheduler: Scheduler) {
    operator fun invoke(vararg reminder: Reminder) {
        scheduler.cancelWork(reminder = reminder)
    }

    operator fun invoke(reminders: List<Reminder>) {
        scheduler.cancelWork(reminders)
    }
}