package ru.vafeen.whisperoftasks.domain.planner.usecase

import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager

class MakeRecoveryNeededUseCase(private val settingsManager: SettingsManager) {
    operator fun invoke() {
        settingsManager.save {
            it.copy(isRecoveryNeeded = true)
        }
    }
}