package ru.vafeen.whisperoftasks.presentation.components.settings_screen

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val SettingsScreenModule = module {
    viewModelOf(::SettingsScreenViewModel)
}