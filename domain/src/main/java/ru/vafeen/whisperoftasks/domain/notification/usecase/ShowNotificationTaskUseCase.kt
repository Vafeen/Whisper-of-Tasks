package ru.vafeen.whisperoftasks.domain.notification.usecase

import ru.vafeen.whisperoftasks.domain.database.usecase.GetReminderByIdOfReminderUseCase
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.notification.NotificationBuilder
import ru.vafeen.whisperoftasks.domain.notification.NotificationService

class ShowNotificationTaskUseCase(
    private val notificationBuilder: NotificationBuilder,
    private val notificationService: NotificationService,
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase,
) {
    suspend operator fun invoke(idOfReminder: Int) {
        getReminderByIdOfReminderUseCase.invoke(idOfReminder)?.let {
            notificationService.showNotification(
                notificationBuilder.createNotificationReminder(
                    it.title,
                    it.text
                )
            )
        }
    }
}