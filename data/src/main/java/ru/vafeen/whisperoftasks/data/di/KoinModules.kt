package ru.vafeen.whisperoftasks.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.DBInfo
import ru.vafeen.whisperoftasks.data.local_database.converters.LocalDateTimeConverters
import ru.vafeen.whisperoftasks.data.network.downloader.Downloader
import ru.vafeen.whisperoftasks.data.network.end_points.DownloadServiceLink
import ru.vafeen.whisperoftasks.data.network.end_points.GHDServiceLink
import ru.vafeen.whisperoftasks.data.network.service.DownloadService
import ru.vafeen.whisperoftasks.data.network.service.GitHubDataService
import ru.vafeen.whisperoftasks.data.shared_preferences.SharedPreferencesValue


val koinDataDatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = DBInfo.NAME
        ).build()
    }
}
val koinDataNetworkModule = module {
    single<GitHubDataService> {
        Retrofit.Builder()
            .baseUrl(GHDServiceLink.BASE_LINK)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubDataService::class.java)
    }
    single<DownloadService> {
        Retrofit.Builder()
            .baseUrl(DownloadServiceLink.BASE_LINK)
            .build().create(DownloadService::class.java)
    }
}

val koinDataServicesModule = module {
    singleOf(::LocalDateTimeConverters)
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }

    singleOf(::Downloader)
}