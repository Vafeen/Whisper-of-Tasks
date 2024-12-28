package ru.vafeen.whisperoftasks.main

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vafeen.whisperoftasks.data.di.koinDataDatabaseModule
import ru.vafeen.whisperoftasks.data.di.koinDataNetworkModule
import ru.vafeen.whisperoftasks.data.di.koinDataServicesModule
import ru.vafeen.whisperoftasks.domain.noui.di.koinDomainDatabaseModule
import ru.vafeen.whisperoftasks.domain.noui.di.koinDomainNetworkModule
import ru.vafeen.whisperoftasks.domain.noui.di.koinDomainServicesModule
import ru.vafeen.whisperoftasks.domain.noui.notification.NotificationChannel
import ru.vafeen.whisperoftasks.domain.noui.notification.createNotificationChannelKClass
import ru.vafeen.whisperoftasks.presentation.koinPresentationViewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                koinDataDatabaseModule,
                koinDataNetworkModule,
                koinDataServicesModule,

                koinDomainDatabaseModule,
                koinDomainServicesModule,
                koinDomainNetworkModule,

                koinPresentationViewModelModule,
            )
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