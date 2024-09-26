package ru.vafeen.reminder.main

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vafeen.reminder.noui.di.koinDIModule
import ru.vafeen.reminder.noui.di.koinDIViewModelModule
import ru.vafeen.reminder.noui.notification.NotificationChannelInfo

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(koinDIModule, koinDIViewModelModule)
        }

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