package ru.vafeen.reminder.noui.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vafeen.reminder.noui.local_database.AppDatabase
import ru.vafeen.reminder.noui.local_database.DBInfo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DIModule {
    @Provides
    @Singleton
    fun injectDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context, klass = AppDatabase::class.java, name = DBInfo.NAME
        ).build()
    }
}
