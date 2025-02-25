package ru.vafeen.whisperoftasks.presentation.components.reminder_recovery_bottomsheet

import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.domain.planner.usecase.ReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager

class ReminderRecoveryBottomSheetViewModel(
    private val reminderRecoveryUseCase: ReminderRecoveryUseCase,
    settingsManager: SettingsManager,
) : ViewModel() {
    val settingsFlow = settingsManager.settingsFlow
    suspend fun recovery() {
        reminderRecoveryUseCase.invoke()
    }
}