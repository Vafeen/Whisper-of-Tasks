package ru.vafeen.whisperoftasks.presentation.di.base

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.presentation.di.koinPresentationViewModelModule

val basePresentationModule = module {
    includes(koinPresentationViewModelModule)
}