package ru.vafeen.whisperoftasks.domain.notification.usecase

import ru.vafeen.whisperoftasks.domain.notification.NotificationBuilder
import ru.vafeen.whisperoftasks.domain.notification.NotificationService

class ShowNotificationTaskUseCase(
    private val notificationBuilder: NotificationBuilder,
    private val notificationService: NotificationService
) {
    operator fun invoke(idOfNotification: Int, title: String, text: String) {
        notificationService.showNotification(
            idOfNotification,
            notificationBuilder.createNotificationReminder(title, text)
        )
    }
}