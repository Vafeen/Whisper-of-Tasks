package ru.vafeen.whisperoftasks.domain.planner.usecase

import kotlinx.coroutines.flow.first
import ru.vafeen.whisperoftasks.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.domain.notification.usecase.NotificationReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import java.time.LocalDateTime

class ReminderRecoveryUseCase(
    private val settingsManager: SettingsManager,
    private val getAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val scheduler: Scheduler
) {
    suspend operator fun invoke() {
        val reminders = getAsFlowRemindersUseCase.invoke().first()
        val dt = LocalDateTime.now()
        reminders.forEach { reminder ->
            scheduler.cancelWork(reminder)
            if ((reminder.dt >= dt || reminder.repeatDuration != RepeatDuration.NoRepeat) && !reminder.isDeleted) {
                scheduler.planWork(reminder)
            }
        }
        settingsManager.save {
            it.copy(isRecoveryNeeded = false)
        }
    }
}