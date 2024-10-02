package ru.vafeen.reminder.noui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.converters.DTConverters
import ru.vafeen.reminder.noui.notification.NotificationService
import ru.vafeen.reminder.noui.planner.Scheduler


val koinServicesDIModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::DTConverters)
    singleOf(::EventCreator)
}