package ru.vafeen.reminder.noui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vafeen.reminder.noui.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val mainScreenViewModelFactory: MainScreenViewModelFactory,
    private val reminderScreenViewModelFactory: ReminderScreenViewModelFactory,
    private val settingsScreenViewModelFactory: SettingsScreenViewModelFactory,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                mainScreenViewModelFactory = mainScreenViewModelFactory,
                reminderScreenViewModelFactory = reminderScreenViewModelFactory,
                settingsScreenViewModelFactory = settingsScreenViewModelFactory
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}