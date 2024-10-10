package ru.vafeen.whisperoftasks.noui.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.whisperoftasks.R
import ru.vafeen.whisperoftasks.main.MainActivity
import kotlin.jvm.java
import kotlin.random.Random


class NotificationService(
    private val context: Context,
) {
    companion object {
        fun createNotificationReminderRecovery(
            title: String = "title",
            text: String = "Hello world!",
        ): Notification {
            val context: Context by inject(
                clazz = Context::class.java
            )
            val pendingIntent = PendingIntent.getActivity(
                context,
                NotificationChannel.ReminderRecovery.REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Builder(
                context,
                NotificationChannel.ReminderRecovery.NOTIFICATION_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.reminder)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        fun createNotificationTask(
            title: String = "title",
            text: String = "Hello world!",
        ): Notification {
            val context: Context by inject(
                clazz = Context::class.java
            )
            val pendingIntent = PendingIntent.getActivity(
                context,
                NotificationChannel.Task.REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Builder(
                context,
                NotificationChannel.Task.NOTIFICATION_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.message)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification(notification: Notification) {
        notificationManager.notify(Random.Default.nextInt(), notification)
    }
}