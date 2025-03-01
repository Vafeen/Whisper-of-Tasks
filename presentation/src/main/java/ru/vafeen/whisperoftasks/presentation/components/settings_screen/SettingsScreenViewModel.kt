package ru.vafeen.whisperoftasks.presentation.components.settings_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vafeen.whisperoftasks.domain.domain_models.Settings
import ru.vafeen.whisperoftasks.domain.planner.Scheduler
import ru.vafeen.whisperoftasks.domain.planner.usecase.ChooseSchedulerUseCase
import ru.vafeen.whisperoftasks.domain.planner.usecase.ReminderRecoveryUseCase
import ru.vafeen.whisperoftasks.domain.shared_preferences.SettingsManager
import ru.vafeen.whisperoftasks.domain.utils.launchIO
import ru.vafeen.whisperoftasks.domain.utils.withContextMain


internal class SettingsScreenViewModel(
    private val settingsManager: SettingsManager,
    private val reminderRecoveryUseCase: ReminderRecoveryUseCase,
    private val chooseSchedulerUseCase: ChooseSchedulerUseCase,
) : ViewModel() {
    val settings = settingsManager.settingsFlow

    fun saveSettings(saving: (Settings) -> Settings) {
        settingsManager.save(saving = saving)
    }

    fun recoveryReminders(context: Context) {
        viewModelScope.launchIO {
            reminderRecoveryUseCase.invoke()
            withContextMain { Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show() }
        }
    }

    fun chooseSchedulerAPIon(choice: Scheduler.Companion.Choice) {
        Log.d("choice", "viewModel")
        viewModelScope.launchIO {
            chooseSchedulerUseCase.invoke(choice)
        }
    }
}