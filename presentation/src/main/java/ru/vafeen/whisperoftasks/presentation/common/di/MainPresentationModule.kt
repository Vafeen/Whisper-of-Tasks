package ru.vafeen.whisperoftasks.presentation.common.di

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.presentation.main.MainActivityDiModule

val MainPresentationModule = module {
    includes(MainActivityDiModule)
}