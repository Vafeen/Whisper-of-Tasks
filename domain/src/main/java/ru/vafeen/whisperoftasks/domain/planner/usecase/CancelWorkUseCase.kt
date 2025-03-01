package ru.vafeen.whisperoftasks.domain.planner.usecase

import org.koin.core.component.KoinComponent
import org.koin.core.qualifier.named
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager

class CancelWorkUseCase(private val getSchedulerDependsOnSettings: GetSchedulerDependsOnSettings) :
    KoinComponent {
    operator fun invoke(vararg reminder: Reminder) = invoke(reminder.toList())
    operator fun invoke(reminders: List<Reminder>) {
        val scheduler = getSchedulerDependsOnSettings.invoke() ?: return
        reminders.forEach { scheduler.cancelWork(it) }
    }
}