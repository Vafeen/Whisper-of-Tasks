package ru.vafeen.whisperoftasks.presentation.components.reminder_recovery_bottomsheet

import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.domain.planner.usecase.ReminderRecoveryUseCase

class ReminderRecoveryBottomSheetViewModel(
    private val reminderRecoveryUseCase: ReminderRecoveryUseCase,
) : ViewModel() {
    suspend fun recovery() {
        reminderRecoveryUseCase.invoke()
    }
}