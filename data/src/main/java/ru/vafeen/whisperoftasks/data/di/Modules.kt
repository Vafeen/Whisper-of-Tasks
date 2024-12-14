package ru.vafeen.whisperoftasks.data.di

import androidx.room.Room
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.data.converters.DurationConverters
import ru.vafeen.whisperoftasks.data.converters.LocalDateConverters
import ru.vafeen.whisperoftasks.data.converters.LocalDateTimeConverters
import ru.vafeen.whisperoftasks.data.converters.ReminderConverter
import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.DBInfo
import ru.vafeen.whisperoftasks.data.local_database.ReminderRepositoryImpl
import ru.vafeen.whisperoftasks.domain.local_database.ReminderRepository

internal val databaseModuleImpl = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = DBInfo.NAME
        ).build()
    }
}
internal val repositoryModuleImpl = module {
    singleOf(::ReminderRepositoryImpl) {
        bind<ReminderRepository>()
    }
}
internal val convertersModule = module {
    singleOf(::DurationConverters)
    singleOf(::LocalDateConverters)
    singleOf(::LocalDateTimeConverters)
    singleOf(::ReminderConverter)
}