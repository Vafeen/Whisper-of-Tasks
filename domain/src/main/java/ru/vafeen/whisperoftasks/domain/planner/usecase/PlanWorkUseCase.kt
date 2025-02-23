package ru.vafeen.whisperoftasks.domain.planner.usecase

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

class PlanWorkUseCase(private val scheduler: Scheduler) {
    operator fun invoke(vararg reminder: Reminder) {
        scheduler.cancelWork(reminder = reminder)
        scheduler.planWork(reminder = reminder)
    }

    operator fun invoke(reminders: List<Reminder>) {
        scheduler.cancelWork(reminders = reminders)
        scheduler.planWork(reminders = reminders)
    }
}