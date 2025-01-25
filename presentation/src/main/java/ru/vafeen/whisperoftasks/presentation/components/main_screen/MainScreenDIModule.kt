package ru.vafeen.whisperoftasks.presentation.components.main_screen

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val MainScreenDIModule = module {
    viewModelOf(::MainScreenViewModel)
}