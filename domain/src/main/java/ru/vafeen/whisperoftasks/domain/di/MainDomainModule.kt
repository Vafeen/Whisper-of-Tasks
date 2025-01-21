package ru.vafeen.whisperoftasks.domain.di

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.domain.database.DatabaseUseCaseModule
import ru.vafeen.whisperoftasks.domain.network.NetworkModule
import ru.vafeen.whisperoftasks.domain.notification.NotificationModule
import ru.vafeen.whisperoftasks.domain.planner.PlannerModule

val MainDomainModule = module {
    includes(PlannerModule, DatabaseUseCaseModule, NetworkModule, NotificationModule)
}