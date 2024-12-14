package ru.vafeen.whisperoftasks.data.di.base

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.data.di.convertersModule
import ru.vafeen.whisperoftasks.data.di.databaseModuleImpl
import ru.vafeen.whisperoftasks.data.di.repositoryModuleImpl

val baseDataModule = module {
    includes(
        databaseModuleImpl,
        repositoryModuleImpl,
        convertersModule
    )
}