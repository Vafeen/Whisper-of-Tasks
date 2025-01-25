package ru.vafeen.whisperoftasks.domain.notification.usecase

import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.notification.NotificationBuilder
import ru.vafeen.whisperoftasks.domain.notification.NotificationService

class ShowNotificationTaskUseCase(
    private val notificationBuilder: NotificationBuilder,
    private val notificationService: NotificationService
) {

    operator fun invoke(reminder: Reminder) {
        notificationService.showNotification(
            notificationBuilder.createNotificationReminder(
                title = reminder.title,
                text = reminder.text
            )
        )
    }
}