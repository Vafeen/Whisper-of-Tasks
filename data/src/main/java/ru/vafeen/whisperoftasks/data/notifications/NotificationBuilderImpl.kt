package ru.vafeen.whisperoftasks.data.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import ru.vafeen.whisperoftasks.domain.MainActivityIntentProvider
import ru.vafeen.whisperoftasks.domain.notification.NotificationBuilder
import ru.vafeen.whisperoftasks.domain.notification.NotificationChannel
import ru.vafeen.whisperoftasks.resources.R

internal class NotificationBuilderImpl(
    private val context: Context,
    private val mainActivityIntentProvider: MainActivityIntentProvider,
) : NotificationBuilder {


    override fun createNotificationReminder(title: String, text: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context,
            NotificationChannel.Task.REQUEST_CODE,
            mainActivityIntentProvider.provideIntent(),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT // Обновляем текущий интент.
        )

        return NotificationCompat.Builder(
            context,
            NotificationChannel.Task.NOTIFICATION_CHANNEL_ID // Используем ID канала.
        )
            .setSmallIcon(R.drawable.message)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // Устанавливаем звук.
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun createNotificationReminderRecovery(
        title: String,
        text: String,
    ): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context,
            NotificationChannel.ReminderRecovery.REQUEST_CODE,
            mainActivityIntentProvider.provideIntent(),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT // Обновляем текущий интент.
        )

        return NotificationCompat.Builder(
            context,
            NotificationChannel.ReminderRecovery.NOTIFICATION_CHANNEL_ID // Используем ID канала.
        )
            .setSmallIcon(R.drawable.reminder)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) // Устанавливаем звук.
            .setContentIntent(pendingIntent)
            .build()
    }
}
