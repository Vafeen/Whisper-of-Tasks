package ru.vafeen.whisperoftasks.data.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import ru.vafeen.whisperoftasks.domain.notification.NotificationService
import kotlin.random.Random

/**
 * Реализация сервиса уведомлений для отображения уведомлений в приложении.
 *
 * @property context Контекст приложения, используемый для доступа к системным службам.
 */
internal class NotificationServiceImpl(
    private val context: Context,
) : NotificationService {

    // Менеджер уведомлений для управления уведомлениями в приложении
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /**
     * Отображает уведомление.
     *
     * @param notification Уведомление, которое будет показано пользователю.
     * Уведомление будет отображено с уникальным идентификатором, сгенерированным случайным образом.
     */
    override fun showNotification(idOfNotification: Int, notification: Notification) {
        notificationManager.notify(
            idOfNotification,
            notification
        ) // Показываем уведомление с уникальным ID
    }
}