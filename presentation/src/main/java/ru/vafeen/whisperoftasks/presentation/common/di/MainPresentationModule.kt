package ru.vafeen.whisperoftasks.presentation.common.di

import org.koin.dsl.module
import ru.vafeen.whisperoftasks.presentation.components.main_screen.MainScreenDIModule
import ru.vafeen.whisperoftasks.presentation.components.reminder_dialog.ReminderDialogModule
import ru.vafeen.whisperoftasks.presentation.components.reminder_recovery_bottomsheet.ReminderRecoveryBottomSheetModule
import ru.vafeen.whisperoftasks.presentation.components.reminders_screen.RemindersScreenModule
import ru.vafeen.whisperoftasks.presentation.components.settings_screen.SettingsScreenModule
import ru.vafeen.whisperoftasks.presentation.components.trash_bin_screen.TrashBinScreenModule
import ru.vafeen.whisperoftasks.presentation.main.MainActivityDiModule

val MainPresentationModule = module {
    includes(
        MainActivityDiModule,
        RemindersScreenModule,
        ReminderDialogModule,
        MainScreenDIModule,
        SettingsScreenModule,
        ReminderRecoveryBottomSheetModule,
        TrashBinScreenModule
    )
}