package ru.vafeen.whisperoftasks.domain.notification.usecase

import android.content.Context
import ru.vafeen.whisperoftasks.domain.notification.NotificationBuilder
import ru.vafeen.whisperoftasks.domain.notification.NotificationService
import ru.vafeen.whisperoftasks.resources.R

class NotificationReminderRecoveryUseCase(
    private val context: Context,
    private val notificationService: NotificationService,
    private val notificationBuilder: NotificationBuilder
) {
    operator fun invoke() {
        notificationService.showNotification(
            notificationBuilder.createNotificationReminderRecovery(
                title = context.getString(R.string.reminder_recovery),
                text = context.getString(R.string.reminders_restored)
            )
        )
    }
}