package ru.vafeen.whisperoftasks.data.di

import androidx.room.Room
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.whisperoftasks.data.converters.DurationConverters
import ru.vafeen.whisperoftasks.data.converters.LocalDateConverters
import ru.vafeen.whisperoftasks.data.converters.LocalDateTimeConverters
import ru.vafeen.whisperoftasks.data.converters.ReleaseConverter
import ru.vafeen.whisperoftasks.data.converters.ReminderConverter
import ru.vafeen.whisperoftasks.data.local_database.AppDatabase
import ru.vafeen.whisperoftasks.data.local_database.DBInfo
import ru.vafeen.whisperoftasks.data.local_database.impl.ReminderRepositoryImpl
import ru.vafeen.whisperoftasks.data.network.end_points.DownloadServiceLink
import ru.vafeen.whisperoftasks.data.network.end_points.GHDServiceLink
import ru.vafeen.whisperoftasks.data.network.impl.DownloadFileRepositoryImpl
import ru.vafeen.whisperoftasks.data.network.impl.DownloaderImpl
import ru.vafeen.whisperoftasks.data.network.impl.ReleaseRepositoryImpl
import ru.vafeen.whisperoftasks.data.network.service.DownloadService
import ru.vafeen.whisperoftasks.data.network.service.GitHubDataService
import ru.vafeen.whisperoftasks.data.planner.impl.EventCreatorImpl
import ru.vafeen.whisperoftasks.data.planner.impl.SchedulerImpl
import ru.vafeen.whisperoftasks.domain.local_database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.network.DownloadFileRepository
import ru.vafeen.whisperoftasks.domain.network.Downloader
import ru.vafeen.whisperoftasks.domain.network.ReleaseRepository
import ru.vafeen.whisperoftasks.domain.planner.EventCreator
import ru.vafeen.whisperoftasks.domain.planner.Scheduler

internal val databaseModuleImpl = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(), klass = AppDatabase::class.java, name = DBInfo.NAME
        ).build()
    }
}
internal val networkModuleImpl = module {
    single<GitHubDataService> {
        Retrofit.Builder().baseUrl(GHDServiceLink.BASE_LINK)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(GitHubDataService::class.java)
    }
    single<DownloadService> {
        Retrofit.Builder().baseUrl(DownloadServiceLink.BASE_LINK).build()
            .create(DownloadService::class.java)
    }
}
internal val databaseRepositoryModuleImpl = module {
    singleOf(::ReminderRepositoryImpl) {
        bind<ReminderRepository>()
    }
}
internal val networkRepositoryModuleImpl = module {
    singleOf(::DownloaderImpl) {
        bind<Downloader>()
    }
    singleOf(::DownloadFileRepositoryImpl) {
        bind<DownloadFileRepository>()
    }
    singleOf(::ReleaseRepositoryImpl) {
        bind<ReleaseRepository>()
    }
}
internal val convertersModule = module {
    singleOf(::DurationConverters)
    singleOf(::LocalDateConverters)
    singleOf(::LocalDateTimeConverters)
    singleOf(::ReleaseConverter)
    singleOf(::ReminderConverter)
}
internal val servicesModuleImpl = module {
    singleOf(::EventCreatorImpl) {
        bind<EventCreator>()
    }
    singleOf(::SchedulerImpl) {
        bind<Scheduler>()
    }
}