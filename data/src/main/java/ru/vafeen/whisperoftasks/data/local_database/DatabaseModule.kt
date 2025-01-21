package ru.vafeen.whisperoftasks.data.local_database

import androidx.room.Room
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.data.local_database.repository.ReminderLocalRepositoryImpl
import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository

internal val DatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = DBInfo.NAME
        ).addMigrations(*AppDatabaseMigrationManager.migrations)
            .build()
    }
    singleOf(::ReminderLocalRepositoryImpl) {
        bind<ReminderLocalRepository>()
    }
}