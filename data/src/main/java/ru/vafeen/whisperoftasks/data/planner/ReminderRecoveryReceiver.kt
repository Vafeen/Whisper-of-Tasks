package ru.vafeen.whisperoftasks.data.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import ru.vafeen.whisperoftasks.domain.notification.usecase.NotificationReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.MakeRecoveryNeededUseCase

class ReminderRecoveryReceiver : BroadcastReceiver(), KoinComponent {
    private val notificationReminderRecoveryUseCase: NotificationReminderRecoveryUseCase =
        getKoin().get()
    private val makeRecoveryNeededUseCase: MakeRecoveryNeededUseCase = getKoin().get()
    override fun onReceive(context: Context?, intent: Intent?) {
        notificationReminderRecoveryUseCase.invoke()
        makeRecoveryNeededUseCase.invoke()
    }
}
