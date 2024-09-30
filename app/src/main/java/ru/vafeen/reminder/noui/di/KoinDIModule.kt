package ru.vafeen.reminder.noui.di


import androidx.room.Room
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.reminder.noui.EventCreator
import ru.vafeen.reminder.noui.local_database.AppDatabase
import ru.vafeen.reminder.noui.local_database.DBInfo
import ru.vafeen.reminder.noui.local_database.DatabaseRepository
import ru.vafeen.reminder.noui.local_database.converters.DTConverters
import ru.vafeen.reminder.noui.notification.NotificationService
import ru.vafeen.reminder.noui.planner.Scheduler


val koinDIModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(), klass = AppDatabase::class.java, name = DBInfo.NAME
        ).build()
    }
    singleOf(::DatabaseRepository)
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::EventCreator)
    singleOf(::DTConverters)
}