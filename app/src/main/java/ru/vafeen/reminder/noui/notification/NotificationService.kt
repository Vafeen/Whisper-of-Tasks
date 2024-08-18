package ru.vafeen.reminder.noui.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.reminder.R
import ru.vafeen.reminder.main.MainActivity
import javax.inject.Inject
import kotlin.random.Random


class NotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val pendingIntent = PendingIntent.getActivity(
        context, NotificationChannelInfo.REQUEST_CODE,
        Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )

    fun showNotification(
        title: String = "title",
        text: String = "Hello world!"
    ) {
        val notification =
            NotificationCompat.Builder(context, NotificationChannelInfo.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        notificationManager.notify(Random.nextInt(), notification)
    }
}