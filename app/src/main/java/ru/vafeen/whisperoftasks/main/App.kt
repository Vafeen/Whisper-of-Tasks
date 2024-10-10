package ru.vafeen.whisperoftasks.main

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vafeen.whisperoftasks.noui.di.koinDIModule
import ru.vafeen.whisperoftasks.noui.di.koinDIViewModelModule
import ru.vafeen.whisperoftasks.noui.di.koinNetworkDIModule
import ru.vafeen.whisperoftasks.noui.di.koinServicesDIModule
import ru.vafeen.whisperoftasks.noui.notification.NotificationChannel
import ru.vafeen.whisperoftasks.noui.notification.createNotificationChannelKClass

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(koinDIModule, koinDIViewModelModule, koinNetworkDIModule, koinServicesDIModule)
        }

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel.Task.createNotificationChannelKClass()
        )
        notificationManager.createNotificationChannel(
            NotificationChannel.ReminderRecovery.createNotificationChannelKClass()
        )

    }
}