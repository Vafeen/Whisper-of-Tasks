package ru.vafeen.whisperoftasks.data.di.base

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.data.di.convertersModule
import ru.vafeen.whisperoftasks.data.di.databaseModuleImpl
import ru.vafeen.whisperoftasks.data.di.databaseRepositoryModuleImpl
import ru.vafeen.whisperoftasks.data.di.networkModuleImpl
import ru.vafeen.whisperoftasks.data.di.networkRepositoryModuleImpl
import ru.vafeen.whisperoftasks.data.di.servicesModuleImpl

val baseDataModule = module {
    includes(
        databaseModuleImpl,
        networkModuleImpl,
        databaseRepositoryModuleImpl,
        convertersModule,
        networkRepositoryModuleImpl,
        servicesModuleImpl
    )
}