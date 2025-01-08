package ru.vafeen.whisperoftasks.presentation.components.reminder_dialog

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val ReminderDialogModule = module {
    viewModelOf(::ReminderDialogViewModel)
}