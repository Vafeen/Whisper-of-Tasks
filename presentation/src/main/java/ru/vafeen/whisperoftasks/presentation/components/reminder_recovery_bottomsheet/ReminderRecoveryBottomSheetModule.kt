package ru.vafeen.whisperoftasks.presentation.components.reminder_recovery_bottomsheet

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val ReminderRecoveryBottomSheetModule = module {
    viewModelOf(::ReminderRecoveryBottomSheetViewModel)
}