package ru.vafeen.whisperoftasks.noui.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.whisperoftasks.ui.common.viewmodel.MainActivityViewModel
import ru.vafeen.whisperoftasks.ui.common.viewmodel.MainScreenViewModel
import ru.vafeen.whisperoftasks.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.whisperoftasks.ui.common.viewmodel.SettingsScreenViewModel

val koinDIViewModelModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::RemindersScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}