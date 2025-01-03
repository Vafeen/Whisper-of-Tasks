package ru.vafeen.whisperoftasks.presentation.main

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val MainActivityDiModule = module {
    viewModelOf(::MainActivityViewModel)
}