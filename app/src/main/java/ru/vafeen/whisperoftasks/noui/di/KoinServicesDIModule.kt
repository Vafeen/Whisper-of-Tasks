package ru.vafeen.whisperoftasks.noui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.noui.EventCreator
import ru.vafeen.whisperoftasks.noui.local_database.converters.LocalDateTimeConverters
import ru.vafeen.whisperoftasks.noui.notification.NotificationService
import ru.vafeen.whisperoftasks.noui.planner.Scheduler
import ru.vafeen.whisperoftasks.noui.shared_preferences.SharedPreferences


val koinServicesDIModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::LocalDateTimeConverters)
    singleOf(::EventCreator)
    singleOf(::SharedPreferences)
}