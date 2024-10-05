package ru.vafeen.reminder.noui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.converters.LocalDateTimeConverters
import ru.vafeen.reminder.noui.notification.NotificationService
import ru.vafeen.reminder.noui.planner.Scheduler
import ru.vafeen.reminder.noui.shared_preferences.SharedPreferences


val koinServicesDIModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::LocalDateTimeConverters)
    singleOf(::EventCreator)
    singleOf(::SharedPreferences)
}