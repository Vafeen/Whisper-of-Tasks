package ru.vafeen.reminder.noui.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.reminder.ui.common.viewmodel.MainActivityViewModel
import ru.vafeen.reminder.ui.common.viewmodel.MainScreenViewModel
import ru.vafeen.reminder.ui.common.viewmodel.RemindersScreenViewModel
import ru.vafeen.reminder.ui.common.viewmodel.SettingsScreenViewModel

val koinDIViewModelModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::RemindersScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}