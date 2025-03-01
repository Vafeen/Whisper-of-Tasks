package ru.vafeen.whisperoftasks.data.planner.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.vafeen.whisperoftasks.domain.notification.usecase.NotificationReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import ru.vafeen.whisperoftasks.domain.planner.usecase.MakeRecoveryNeededUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager

class AlarmManagerReminderRecoveryReceiver : BroadcastReceiver(), KoinComponent {
    private val notificationReminderRecoveryUseCase: NotificationReminderRecoveryUseCase by inject()
    private val makeRecoveryNeededUseCase: MakeRecoveryNeededUseCase by inject()
    private val settingsManager: SettingsManager = getKoin().get()
    override fun onReceive(context: Context?, intent: Intent?) {
        if (settingsManager.settingsFlow.value.schedulerChoice == Scheduler.Companion.Choice.ALARM_MANAGER.value) {
            notificationReminderRecoveryUseCase.invoke()
            makeRecoveryNeededUseCase.invoke()
        }
    }
}
