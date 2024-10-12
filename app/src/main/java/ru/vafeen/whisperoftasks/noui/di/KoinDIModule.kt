package ru.vafeen.whisperoftasks.noui.di


import androidx.room.Room
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.noui.local_database.AppDatabase
import ru.vafeen.whisperoftasks.noui.local_database.DBInfo
import ru.vafeen.whisperoftasks.noui.local_database.DatabaseRepository


val koinDatabaseDIModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(), klass = AppDatabase::class.java, name = DBInfo.NAME
        ).build()
    }
    singleOf(::DatabaseRepository)
}