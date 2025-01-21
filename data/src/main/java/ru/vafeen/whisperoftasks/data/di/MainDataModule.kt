package ru.vafeen.whisperoftasks.data.di

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.data.converters.ConvertersModule
import ru.vafeen.whisperoftasks.data.local_database.DatabaseModule
import ru.vafeen.whisperoftasks.data.network.NetworkModule
import ru.vafeen.whisperoftasks.data.notifications.NotificationsModule
import ru.vafeen.whisperoftasks.data.planner.SchedulerModule
import ru.vafeen.whisperoftasks.data.shared_preferences.SharedPreferencesModule

val MainDataModule = module {
    includes(
        SchedulerModule,
        DatabaseModule,
        ConvertersModule,
        NetworkModule,
        NotificationsModule,
        SharedPreferencesModule
    )
}