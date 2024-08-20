package ru.vafeen.reminder.main

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import ru.vafeen.reminder.noui.notification.NotificationChannelInfo
import ru.vafeen.reminder.noui.work_mananger.MyWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory

    override val workManagerConfiguration: Configuration by lazy {
        Configuration.Builder()
            .setWorkerFactory(myWorkerFactory)
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            NotificationChannelInfo.NOTIFICATION_CHANNEL_ID,
            NotificationChannelInfo.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
}