package ru.vafeen.whisperoftasks.presentation.components.reminders_screen

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val RemindersScreenModule = module {
    viewModelOf(::RemindersScreenViewModel)
}