package ru.vafeen.whisperoftasks.presentation

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.MainActivityViewModel
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.MainScreenViewModel
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.whisperoftasks.presentation.common.viewmodel.SettingsScreenViewModel


val koinPresentationViewModelModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::RemindersScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}