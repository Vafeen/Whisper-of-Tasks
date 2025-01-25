package ru.vafeen.whisperoftasks.domain.notification

import android.app.Notification

/**
 * Интерфейс для работы с уведомлениями.
 */
interface NotificationService {

    /**
     * Показывает указанное уведомление.
     *
     * @param notification Уведомление, которое нужно отобразить.
     */
    fun showNotification(notification: Notification)
}
