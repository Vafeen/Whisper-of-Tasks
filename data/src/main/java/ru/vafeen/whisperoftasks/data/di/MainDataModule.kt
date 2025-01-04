package ru.vafeen.whisperoftasks.data.di

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.data.mock.database.DatabaseModule

val MainDataModule = module {
    includes(DatabaseModule)
}