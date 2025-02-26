package ru.vafeen.whisperoftasks.domain.planner.usecase

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

class PlanWorkUseCase(private val scheduler: Scheduler) {
    operator fun invoke(vararg reminder: Reminder) = invoke(reminder.toList())
    operator fun invoke(reminders: List<Reminder>) = reminders.forEach {
        scheduler.cancelWork(it)
        if (it.isNotificationNeeded) scheduler.planWork(it)
    }
}