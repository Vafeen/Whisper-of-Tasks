package ru.vafeen.whisperoftasks.domain.notification

import android.app.Notification

/**
 * Интерфейс для построения уведомлений.
 */
interface NotificationBuilder {

    /**
     * Создает уведомле о напоминании
     **/
    fun createNotificationReminder(
        title: String = "title",
        text: String = "Hello world!",
    ): Notification


    /**
     * Создает уведомление для восстановления напоминания.
     *
     * @param title Заголовок уведомления (по умолчанию "title").
     * @param text Текст уведомления (по умолчанию "Hello world!").
     * @return Объект [Notification], представляющий созданное уведомление.
     */
    fun createNotificationReminderRecovery(
        title: String = "title",
        text: String = "Hello world!",
    ): Notification
}
