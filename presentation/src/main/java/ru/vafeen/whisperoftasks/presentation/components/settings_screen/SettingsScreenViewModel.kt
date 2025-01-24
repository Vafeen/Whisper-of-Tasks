package ru.vafeen.whisperoftasks.presentation.components.settings_screen

import androidx.lifecycle.ViewModel
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager


internal class SettingsScreenViewModel(private val settingsManager: SettingsManager) : ViewModel() {
    val settings = settingsManager.settingsFlow

    fun saveSettings(saving: (Settings) -> Settings) {
        settingsManager.save(saving = saving)
    }

}