package ru.vafeen.whisperoftasks.domain.noui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.domain.database.usecase.UpdateAllRemindersUseCase
import ru.vafeen.whisperoftasks.data.local_database.DatabaseRepository
import ru.vafeen.whisperoftasks.data.network.repository.NetworkRepository
import ru.vafeen.whisperoftasks.domain.noui.EventCreator
import ru.vafeen.whisperoftasks.domain.noui.database.DatabaseRepositoryImpl
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.GetReminderByIdOfReminderUseCase
import ru.vafeen.whisperoftasks.domain.noui.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.whisperoftasks.domain.noui.network.NetworkRepositoryImpl
import ru.vafeen.whisperoftasks.domain.noui.network.usecase.DownloadFileUseCase
import ru.vafeen.whisperoftasks.domain.noui.network.usecase.GetLatestReleaseUseCase
import ru.vafeen.whisperoftasks.domain.noui.notification.NotificationService
import ru.vafeen.whisperoftasks.domain.noui.planner.Scheduler


val koinDomainServicesModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::EventCreator)
}

val koinDomainDatabaseModule = module {
    singleOf(::DeleteAllRemindersUseCase)
    singleOf(::GetAllAsFlowRemindersUseCase)
    singleOf(::GetReminderByIdOfReminderUseCase)
    singleOf(::InsertAllRemindersUseCase)
    singleOf(::UpdateAllRemindersUseCase)
    single<DatabaseRepository> {
        DatabaseRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}

val koinDomainNetworkModule = module {
    singleOf(::DownloadFileUseCase)
    singleOf(::GetLatestReleaseUseCase)
    single<NetworkRepository> {
        NetworkRepositoryImpl(get(), get())
    }
}
